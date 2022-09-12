// Generated by view binder compiler. Do not edit!
package com.example.chat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.chat.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityShowChatBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout bottomNavigation;

  @NonNull
  public final Button btnSendMessage;

  @NonNull
  public final ImageView imBack;

  @NonNull
  public final ImageView imProfile;

  @NonNull
  public final EditText inputMessage;

  @NonNull
  public final RecyclerView recListChat;

  @NonNull
  public final TextView titleNick;

  @NonNull
  public final LinearLayout topNav;

  private ActivityShowChatBinding(@NonNull ConstraintLayout rootView,
      @NonNull LinearLayout bottomNavigation, @NonNull Button btnSendMessage,
      @NonNull ImageView imBack, @NonNull ImageView imProfile, @NonNull EditText inputMessage,
      @NonNull RecyclerView recListChat, @NonNull TextView titleNick,
      @NonNull LinearLayout topNav) {
    this.rootView = rootView;
    this.bottomNavigation = bottomNavigation;
    this.btnSendMessage = btnSendMessage;
    this.imBack = imBack;
    this.imProfile = imProfile;
    this.inputMessage = inputMessage;
    this.recListChat = recListChat;
    this.titleNick = titleNick;
    this.topNav = topNav;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityShowChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityShowChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_show_chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityShowChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomNavigation;
      LinearLayout bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.btnSendMessage;
      Button btnSendMessage = ViewBindings.findChildViewById(rootView, id);
      if (btnSendMessage == null) {
        break missingId;
      }

      id = R.id.imBack;
      ImageView imBack = ViewBindings.findChildViewById(rootView, id);
      if (imBack == null) {
        break missingId;
      }

      id = R.id.imProfile;
      ImageView imProfile = ViewBindings.findChildViewById(rootView, id);
      if (imProfile == null) {
        break missingId;
      }

      id = R.id.inputMessage;
      EditText inputMessage = ViewBindings.findChildViewById(rootView, id);
      if (inputMessage == null) {
        break missingId;
      }

      id = R.id.recListChat;
      RecyclerView recListChat = ViewBindings.findChildViewById(rootView, id);
      if (recListChat == null) {
        break missingId;
      }

      id = R.id.titleNick;
      TextView titleNick = ViewBindings.findChildViewById(rootView, id);
      if (titleNick == null) {
        break missingId;
      }

      id = R.id.topNav;
      LinearLayout topNav = ViewBindings.findChildViewById(rootView, id);
      if (topNav == null) {
        break missingId;
      }

      return new ActivityShowChatBinding((ConstraintLayout) rootView, bottomNavigation,
          btnSendMessage, imBack, imProfile, inputMessage, recListChat, titleNick, topNav);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}