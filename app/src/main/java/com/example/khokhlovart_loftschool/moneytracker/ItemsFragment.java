package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.khokhlovart_loftschool.moneytracker.Api.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Dom on 02.11.2017.
 */

public class ItemsFragment extends Fragment {
    public static final int PAGE_UNKNOWN = -1;
    public static final int PAGE_EXPENSE = 0;
    public static final int PAGE_INCOMES = 1;

    private static final String KEY_TYPE = "TYPE";
    private int type = -1;
    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ITEMS_ADD = 1;
    private static final int LOADER_ITEMS_DELETE = 2;
    private ActionMode actionMode;
    private FloatingActionButton fab;
    private ItemCosts newAddItem;
    private List<Integer> idItemsToDelete = new ArrayList<>();

    private ItemsAdaptor adaptor;
    private Api api;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type    = getArguments().getInt(KEY_TYPE, PAGE_UNKNOWN);
        adaptor = new ItemsAdaptor(getContext(), type);
        api     = ((App) getActivity().getApplication()).getApi();
    }

    public static ItemsFragment CreateItemsFragment(int type) {
        ItemsFragment f = new ItemsFragment();
        Bundle b = new Bundle();
        b.putInt(ItemsFragment.KEY_TYPE, type);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(type == PAGE_UNKNOWN ? R.layout.error_layout : R.layout.items_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (type != PAGE_UNKNOWN) {
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadItems();
                }
            });
            RecyclerView itemsRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
            itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            itemsRecyclerView.setAdapter(adaptor);
            adaptor.setListener(new ItemsAdapterListener() {
                @Override
                public void onItemClick(ItemCosts item, int position) {
                    if (isInActionMode()) {
                        adaptor.toggleSelection(position);
                        actionMode.setTitle(getString(R.string.select_count_element, adaptor.getSelectedItemCount()));
                    }
                }

                @Override
                public void onItemLongClick(ItemCosts item, int position) {
                    if (isInActionMode()) {
                        return;
                    }
                    actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                    adaptor.toggleSelection(position);
                    actionMode.setTitle(getString(R.string.select_count_element, adaptor.getSelectedItemCount()));
                }

                private boolean isInActionMode() {
                    return actionMode != null;
                }
            });

            fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AddActivity.class);
                    intent.putExtra(AddActivity.PAGE_TYPE, type);
                    startActivityForResult(intent, AddActivity.RCT_ADD_ITEM);
                    //AddActivity.startForResult(ItemsFragment.this, type, AddActivity.RCT_ADD_ITEM);
                }
            });
            loadItems();
        }
    }


    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddActivity.RCT_ADD_ITEM && resultCode == RESULT_OK) {
            ItemCosts item = (ItemCosts) data.getSerializableExtra(AddActivity.RESULT_ITEM);
            addItem(item);
            adaptor.addItem(item);
        }
    }

    private void removeSelectedItems() {

        for (int i = adaptor.getSelectedItems().size() - 1; i >= 0; i--) {
            deleteItem(adaptor.getItemByPosition(adaptor.getSelectedItems().get(i)));
        }

        for (int i = adaptor.getSelectedItems().size() - 1; i >= 0; i--) {
            adaptor.remove(adaptor.getSelectedItems().get(i));
        }
    }

    public void stopActionMode() {
        actionMode.finish();
    }

    public void deleteSelectedItems() {
        removeSelectedItems();
        stopActionMode();
    }


    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            fab.setVisibility(View.GONE);
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }


        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    showDialog();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            fab.setVisibility(View.VISIBLE);
            adaptor.clearSelections();
            actionMode = null;
        }

    };

    private void showDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setItemsFragment(this);
        dialog.show(getFragmentManager(), "Confirmation");
    }


    /**********************************************************************************************************************
     * *********************************** Методы сервиса *******************************************************************
     **********************************************************************************************************************/
    private void deleteItem(ItemCosts delItem) {
        idItemsToDelete.add(delItem.id);
        //new_del_item = delItem;

        getLoaderManager().initLoader(LOADER_ITEMS_DELETE, null, new LoaderManager.LoaderCallbacks() {
            @Override
            public Loader onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader(getContext()) {
                    @Override
                    public Boolean loadInBackground() {
                        try {
                            for (Integer id : idItemsToDelete)
                            {
                                api.items_remove((int)id).execute().body();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader loader, Object data) {}

            @Override
            public void onLoaderReset(Loader loader) {}

        }).forceLoad();
    }

    private void addItem(ItemCosts new_item) {
        newAddItem = new_item;
        getLoaderManager().initLoader(LOADER_ITEMS_ADD, null, new LoaderManager.LoaderCallbacks() {
            @Override
            public Loader onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader(getContext()) {
                    @Override
                    public Boolean loadInBackground() {
                        try {
                            api.items_add(newAddItem.price, newAddItem.name, (type == PAGE_EXPENSE ? "expense" : "income")).execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader loader, Object data) {

            }

            @Override
            public void onLoaderReset(Loader loader) {
            }

        }).forceLoad();
    }

    private void loadItems() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<ItemCosts>>() {
            @Override
            public Loader<List<ItemCosts>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<List<ItemCosts>>(getContext()) {
                    @Override

                    public List<ItemCosts> loadInBackground() {
                        try {
                            refreshLayout.setRefreshing(false);
                            List<ItemCosts> items = api.items( (type == PAGE_EXPENSE ? "expense" : "income") ).execute().body();
                            return items;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<ItemCosts>> loader, List<ItemCosts> items) {
                if (items == null) {
                    adaptor.setItems(new ArrayList<ItemCosts>());
                    showError(getContext().getResources().getString(R.string.error_msg));
                } else {
                    adaptor.setItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<ItemCosts>> loader) {

            }
        }).forceLoad();
    }
}
