package com.poly.bookmanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.poly.bookmanager.R;
import com.poly.bookmanager.model.User;
import com.poly.bookmanager.projectDAO.UserDAO;

import java.util.List;


public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {
    public List<User> userList;
    public Context context;
    public UserDAO userDAO;
    public LayoutInflater inflater;
    public AdapterUser(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        userDAO = new UserDAO(context);
    }

    @NonNull
    @Override
    public AdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_user, parent, false);
        return new AdapterUser.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.ViewHolder holder, final int position) {
        final User user = userList.get(position);
        holder.idNameUser.setText(" Họ và tên: "+user.getName());
        holder.idPhone.setText(" Phone: "+user.getPhone());
        holder.idUsername.setText(" Username: "+ user.getUsername());
        holder.imgOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu pm = new PopupMenu(context, view);
                pm.getMenuInflater().inflate(R.menu.more_user, pm.getMenu());
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_edit:
                                updateUser(position, view);
                                break;

                            case R.id.item_delete:
                                userList.remove(position);
                                userDAO.deleteUser(user.getUsername());
                                notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                pm.show();
            }
            private void updateUser(int position, View view) {
                final AlertDialog.Builder builder =new AlertDialog.Builder(view.getContext());
                View a = inflater.inflate(R.layout.dialog_edituser,null);
                builder.setView(a);
                final Dialog dialog = builder.create();
                final EditText edEditUser = a.findViewById(R.id.edEditUser);
                final EditText edEditPhone = a.findViewById(R.id.edEditPhone);
                final EditText edFullName = a.findViewById(R.id.edFullName);
                final Button btnEditUser = a.findViewById(R.id.btnEditUser);
                if (userList.get(position) != null) {
                    edEditUser.setText(user.getUsername());
                    edEditPhone.setText(user.getPhone());
                    edFullName.setText(user.getName());
                }
                btnEditUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String name = edEditUser.getText().toString();
                        final String phone = edEditPhone.getText().toString();
                        userDAO.changeUser(user.getUsername(), phone, name);
                        userList = userDAO.getAllUser();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idNameUser, idUsername, idPhone;
        public ImageView imgOthers;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idNameUser = itemView.findViewById(R.id.idNameUser);
            idUsername = itemView.findViewById(R.id.idUsername);
            idPhone = itemView.findViewById(R.id.idPhoneUser);
            imgOthers = itemView.findViewById(R.id.imgOthers);
        }
    }
}