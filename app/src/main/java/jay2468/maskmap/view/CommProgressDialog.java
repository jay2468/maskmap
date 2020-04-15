package jay2468.maskmap.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import jay2468.maskmap.R;

/**
 * Created by 00298006 on 17/7/21.
 */

public class CommProgressDialog extends Dialog {
    private Context context = null;
    private int anim=0;
    private static CommProgressDialog commProgressDialog = null;

    public CommProgressDialog(Context context){
        super(context);
        this.context = context;
    }

    public CommProgressDialog(Context context, int theme, int anim) {
        super(context, theme);
        this.anim=anim;
    }

    public static CommProgressDialog createDialog(Context context, int anim){
        commProgressDialog = new CommProgressDialog(context, R.style.CommProgressDialog ,anim);
        commProgressDialog.setContentView(R.layout.im_comm_progress_dialog);
        commProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        commProgressDialog.setCancelable(false);
        return commProgressDialog;
    }



    public void onWindowFocusChanged(boolean hasFocus){

        if (commProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) commProgressDialog.findViewById(R.id.iv_loading);
        if(anim!=0) {
            imageView.setBackgroundResource(anim);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    /**
     * 设置标题
     * @param strTitle
     * @return
     */
    public CommProgressDialog setTitile(String strTitle){
        return commProgressDialog;
    }

    /**
     * 设置提示内容
     * @param strMessage
     * @return
     */
    public CommProgressDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)commProgressDialog.findViewById(R.id.tv_loading_msg);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return commProgressDialog;
    }

    /**屏蔽返回键**/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode== KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}