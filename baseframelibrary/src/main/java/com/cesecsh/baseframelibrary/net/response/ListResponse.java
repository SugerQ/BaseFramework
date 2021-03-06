package com.cesecsh.baseframelibrary.net.response;


import com.cesecsh.baseframelibrary.manager.ActivityManager;
import com.cesecsh.baseframelibrary.manager.AlertDialogManager;
import com.cesecsh.baseframelibrary.net.constant.CodeState;
import com.cesecsh.baseframelibrary.net.json.ListJson;
import com.cesecsh.baseframelibrary.ui.widget.ToastView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by 上海中电
 * on 2017/1/3
 * 继承 Subscriber 并且对ListJson处理，
 * 成功：返回list对象，
 * 失败：给出相应的提示
 */

public abstract class ListResponse<T> extends Subscriber<ListJson> {

    @Override
    public void onNext(ListJson listJson) {
        if (listJson != null) {
            switch (Integer.valueOf(listJson.getCode())) {
                case CodeState.SUCCESS:
                    onSuccess((List<T>) listJson.getObjs());
                    break;
                case CodeState.LOGIN_OVERDUE:
                    AlertDialogManager.showLoginOverdue(ActivityManager.getInstance().getTaskTop());
                    break;
                case CodeState.PERMISSION_DENY:
                    ToastView.getInstance().toast(ActivityManager.getInstance().getTaskTop(), CodeState.getMessage(CodeState.PERMISSION_DENY), ToastView.TYPE_WARNING);
                    break;
                case CodeState.PARAM_ERROR:
                    ToastView.getInstance().toast(ActivityManager.getInstance().getTaskTop(), listJson.getMessage(), ToastView.TYPE_WARNING);
                    break;
                case CodeState.SERVER_ERROR:
                    ToastView.getInstance().toast(ActivityManager.getInstance().getTaskTop(), CodeState.getMessage(CodeState.SERVER_ERROR), ToastView.TYPE_WARNING);
                    break;
                default:
            }
        }
    }


    public abstract void onSuccess(List<T> objs);
}
