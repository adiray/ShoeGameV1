package com.example.dell.shoegamev1.responseobjects;


import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.items.AbstractItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


// AbstractItem<ArtistResponse, ArtistResponse.artistResponseViewHolder>

public class BkUserObject extends AbstractItem<BkUserObject, BkUserObject.BkUserObjectViewHolder> {

    @SerializedName("lastLogin")
    private Object lastLogin;

    @SerializedName("userStatus")
    private String userStatus;

    @SerializedName("logged_in")
    private boolean loggedIn;

    @SerializedName("created")
    private long created;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("ownerId")
    private String ownerId;

    @SerializedName("socialAccount")
    private String socialAccount;

    @SerializedName("___class")
    private String ___class;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("blUserLocale")
    private String blUserLocale;

    @SerializedName("updated")
    private long updated;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("objectId")
    private String objectId;

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setSocialAccount(String socialAccount) {
        this.socialAccount = socialAccount;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    public void setClass(String ___class) {
        this.___class = ___class;
    }

    public String get___class() {
        return ___class;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setBlUserLocale(String blUserLocale) {
        this.blUserLocale = blUserLocale;
    }

    public String getBlUserLocale() {
        return blUserLocale;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getUpdated() {
        return updated;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        return
                "BkUserObject{" +
                        "lastLogin = '" + lastLogin + '\'' +
                        ",userStatus = '" + userStatus + '\'' +
                        ",logged_in = '" + loggedIn + '\'' +
                        ",created = '" + created + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",ownerId = '" + ownerId + '\'' +
                        ",socialAccount = '" + socialAccount + '\'' +
                        ",___class = '" + ___class + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",blUserLocale = '" + blUserLocale + '\'' +
                        ",updated = '" + updated + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        ",objectId = '" + objectId + '\'' +
                        "}";
    }



    /*******************************************************************************************************************************************************
     * FAST ADAPTER CODE STARTS HERE
     *
     */




    @NonNull
    @Override
    public BkUserObjectViewHolder getViewHolder(View v) {
        return new BkUserObjectViewHolder(v);
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }







    protected static class BkUserObjectViewHolder extends RecyclerView.ViewHolder {


        public BkUserObjectViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }




}






















    /*protected static class artistResponseViewHolder extends RecyclerView.ViewHolder {

        //declaring the views
        ImageView artist_image_vh;
        TextView artist_name_vh;

        public artistResponseViewHolder(View itemView) {
            super(itemView);

            //assigning the previously declared views
            artist_image_vh = itemView.findViewById(R.id.music_activity_artist_rec_view_image);
            artist_name_vh = itemView.findViewById(R.id.music_activity_artist_rec_view_name);
        }
    }*/