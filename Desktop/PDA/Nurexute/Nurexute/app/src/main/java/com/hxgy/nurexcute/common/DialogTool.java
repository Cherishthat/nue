package com.hxgy.nurexcute.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

public class DialogTool {
	public static final int NO_ICON = -1;

	/**
	 * 创建消息对话框
	 * 
	 * @param context
	 *            上下文必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
	 * @param title
	 *            标题必填
	 * @param message
	 *            显示内容 必填
	 * @param btnName
	 *            按钮名称必填
	 * @param listener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口必填
	 * @return
	 */

	public static Dialog createMessageDialog(Context context, String title,
			String message, String btnName, OnClickListener listener, int iconId) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (iconId != NO_ICON) {
			// 设置对话框图标
			builder.setIcon(iconId);

		}

		// 设置对话框标题
		builder.setTitle(title);
		// 设置对话框消息
		builder.setMessage(message);

		// 设置按钮
		builder.setPositiveButton(btnName, listener);
		// 创建一个消息对话框
		dialog = builder.create();
		return dialog;
	}

	public static Dialog createConfirmDialog(Context context, String title,
			String message, String positiveBtnName, String negativeBtnName,
			DialogInterface.OnClickListener positiveBtnListener,
			DialogInterface.OnClickListener negativeBtnListener, int iconId) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (iconId != NO_ICON) {
			builder.setIcon(iconId);

		}
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		builder.setNeutralButton(negativeBtnName, negativeBtnListener);
		dialog = builder.create();

		return dialog;
	}

	/**
	 * 创建列表对话框
	 * 
	 * @param context
	 *            上下文必填
	 * @param iconId
	 *            图标，如：R.drawable.icon 或 DialogTool.NO_ICON 必填
	 * @param title
	 *            标题必填
	 * @param itemsString
	 *            列表项必填
	 * @param negativeBtnName
	 *            取消按钮名称必填
	 * @param negativeBtnListener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口必填
	 * @return
	 */

	public static Dialog createListDialog(Context context, String title,
			String[] itemsString,

			String negativeBtnName, OnClickListener negativeBtnListener,
			OnClickListener itemClickListener, int iconId) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (iconId != NO_ICON) {

			// 设置对话框图标
			builder.setIcon(iconId);
		}
		// 设置对话框标题
		builder.setTitle(title);

		// 设置列表选项
		builder.setItems(itemsString, itemClickListener);
		// 设置确定按钮
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		// 创建一个消息对话框
		dialog = builder.create();
		return dialog;

	}

	/**
	 * 创建自定义（含确认、取消）对话框
	 * 
	 * @param context
	 *            上下文必填
	 * @param iconId
	 *            图标，如R.drawable.icon 或 DialogTool.NO_ICON 必填
	 * @param title
	 *            标题必填
	 * @param positiveBtnName确定按钮名称必填
	 * @param negativeBtnName
	 *            消按钮名称必填
	 * @param positiveBtnListener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口必填
	 * @param negativeBtnListener
	 *            监听器，需实现android.content.DialogInterface.OnClickListener接口必填
	 * @param view
	 *            对话框中自定义视图必填
	 * @return
	 */

	public static Dialog createRandomDialog(Context context, String title,
			String positiveBtnName, String negativeBtnName,
			OnClickListener positiveBtnListener,
			OnClickListener negativeBtnListener, View view, int iconId)

	{
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		if (iconId != NO_ICON) {

			// 设置对话框图标
			builder.setIcon(iconId);
		}

		// 设置对话框标题
		builder.setTitle(title);
		builder.setView(view);
		// 设置确定按钮
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		// 设置确定按钮
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		// 创建一个消息对话框
		dialog = builder.create();
		return dialog;

	}
	
	public static Dialog createSkinDialog(Context context, String title,
			String positiveBtnName, String negativeBtnName,String negativeBtnNameTwo,
			OnClickListener positiveBtnListener,
			OnClickListener negativeBtnListener, OnClickListener negativeBtnListenerTwo, int iconId)

	{
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		if (iconId != NO_ICON) {

			// 设置对话框图标
			builder.setIcon(iconId);
		}

		// 设置对话框标题
		builder.setTitle(title);
//		builder.setView(view);
		// 设置确定按钮
		builder.setPositiveButton(positiveBtnName, positiveBtnListener);
		builder.setNegativeButton(negativeBtnName, negativeBtnListener);
		builder.setNeutralButton(negativeBtnNameTwo, negativeBtnListenerTwo);
		// 创建一个消息对话框
		dialog = builder.create();
		return dialog;

	}

}
