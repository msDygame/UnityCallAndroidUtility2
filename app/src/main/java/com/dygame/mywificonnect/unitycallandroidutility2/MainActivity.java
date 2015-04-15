package com.dygame.mywificonnect.unitycallandroidutility2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


public class MainActivity extends UnityPlayerActivity
{
    protected PopupWindow mPopupWindow;
    protected String sVersionName = "1.0" ;
    protected String sVersionCode = "1" ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_main);
        PackageInfo p = getVersionInfo(this) ;
        if (p != null)
        {
            sVersionName = p.versionName ;
            sVersionCode = "" + p.versionCode;
            //20150415:PackageInfo cant get value...sVersionName and sVersionCode get a empty string ,... extends UnityPlayerActivity?
            //sVersionCode = "RUN_HERE" ; //it get "RUN_HERE"...
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     *  Unity call android method to share text.
     *  @param subject  for Intent.putExtra(EXTRA_SUBJECT,subject);  body for Intent.putExtra(EXTRA_TEXT.body);
     *  @return void
     */
    public void androidShareText(String subject, String body)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    /**
     * Unity call android method to show a Toast. (unity 呼叫 Android 快顯訊息提示(toast))
     * @param sText is  for Toast.makeText(sText) ;
     * @return void
     */
    public void androidShowToast(final String sText)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this, sText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Unity call android method to show a AlertDialog ,  two button dialog , OK and CANCEL. (unity 呼叫 android 兩個按鈕的彈出式對話盒(AlertDialog))
     * @param sTitle , alertDialog ttitle
     * @param sMessage , alertDialog Message
     * @param sLeftButton ,  The text to display in the alertDialog Positive button. (left button , it mean "OK")
     * @param sRightButton ,  The text to display in the alertDialog Negative button. (right button , it mean "CANCEL")
     */
    public void androidShowAlertDialog(final String sTitle , final String sMessage , final String sLeftButton , final String sRightButton)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(sTitle)
                        .setMessage(sMessage)
                        .setPositiveButton(sLeftButton, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Android send msg to Unity, "掛載此腳本的物件名(AlertDialogOnClick.cs在Unity3D裡是放在攝影機上...)", "函式名稱", "參數"
                                UnityPlayer.UnitySendMessage("Main Camera", "AlertDialogOnClick", "POSITIVE");
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(sRightButton, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                UnityPlayer.UnitySendMessage("Main Camera", "AlertDialogOnClick", "NEGATIVE");
                                dialog.dismiss();
                            }
                        })
                        .create();
                //
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });
    }

    /**
     * Unity call android method to show a AlertDialog ,  two button dialog , OK and CANCEL. (unity 呼叫 android 一個按鈕的彈出式對話盒(AlertDialog))
     * @param sTitle , alertDialog ttitle
     * @param sMessage , alertDialog Message
     * @param sButtonTips ,  The text to display in the alertDialog Positive button. (left button , it mean "OK")
     */
    public void androidShowAlertDialog(final String sTitle , final String sMessage , final String sButtonTips)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(sTitle)
                        .setMessage(sMessage)
                        .setPositiveButton(sButtonTips, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Android send msg to Unity, "掛載腳本的Unity3D的物件名", "函式名稱", "參數"
                                UnityPlayer.UnitySendMessage("Main Camera", "AlertDialogOnClick", "POSITIVE");
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });
    }

    /**
     * Unity call android method to show a AlertDialog ,  two button dialog , OK and CANCEL. (unity 呼叫 android 三個按鈕的彈出式對話盒(AlertDialog))
     * @param sTitle , alertDialog ttitle
     * @param sMessage , alertDialog Message
     * @param sLeftButton ,  The text to display in the alertDialog Positive button. (left button , it mean "OK")
     * @param sRightButton ,  The text to display in the alertDialog Negative button. (right button , it mean "CANCEL")
     * @param sMiddleButton ,  The text to display in the alertDialog Neutral button. (Middle button , it mean "APPLY" , etc..)
     */
    public void androidShowAlertDialog(final String sTitle , final String sMessage , final String sLeftButton , final String sMiddleButton , final String sRightButton)
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(sTitle)
                        .setMessage(sMessage)
                        .setPositiveButton(sLeftButton, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //Android send msg to Unity, "掛載腳本的Unity3D的物件名", "函式名稱", "參數"
                                UnityPlayer.UnitySendMessage( "Main Camera", "UnityAlertDialogOnClick", "POSITIVE" );
                                dialog.dismiss() ;
                            }
                        })
                        .setNegativeButton(sRightButton, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                UnityPlayer.UnitySendMessage( "Main Camera", "UnityAlertDialogOnClick", "NEGATIVE" );
                                dialog.dismiss() ;
                            }
                        })
                        .setNeutralButton(sMiddleButton, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                UnityPlayer.UnitySendMessage( "Main Camera", "UnityAlertDialogOnClick", "NEUTRAL" );
                                dialog.dismiss() ;
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show() ;
            }
        });
    }
    /**
     *  call android finish()
     */
    public void exit()
    {
        finish() ;
    }
    /**
     *  call android finish()
     */
    public void quit()
    {
        finish() ;
    }

    /**
     * Unity call android method to show a popupWindow ,  to instead menu.(unity 呼叫 android 彈出式視窗取代原本menu)
     * @param iWidth
     * @param iHeight
     * @param sUpButton , default 3 button.
     * @param sMiddleButton , default 3 button.
     * @param sBottomButton , default 3 button.
     */
    public void androidShowPopupWindow(int iWidth,int iHeight,final String sUpButton , final String sMiddleButton , final String sBottomButton)
    {
        //20150415:the method doesnot work at jar for unity call....
        if (null != mPopupWindow)
        {
            mPopupWindow.dismiss();//已開過就關了它
            return;
        }
        else
        {
            LinearLayout pScreenView = (LinearLayout)findViewById(R.id.LinearLayoutScreen);
            //沒開過就開了它
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View pwView = layoutInflater.inflate(R.layout.popup_window, null);
            Button pButton1 = (Button) pwView.findViewById(R.id.button01);
            Button pButton2 = (Button) pwView.findViewById(R.id.button02);
            Button pButton3 = (Button) pwView.findViewById(R.id.button03);
            pButton1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    UnityPlayer.UnitySendMessage( "Main Camera", "UnityPopupWindowOnClick", "TOP" );
                    mPopupWindow.dismiss();
                }
            });
            pButton2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    UnityPlayer.UnitySendMessage( "Main Camera", "UnityPopupWindowOnClick", "MIDDLE" );
                    mPopupWindow.dismiss();
                }
            });
            pButton3.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    UnityPlayer.UnitySendMessage( "Main Camera", "UnityPopupWindowOnClick", "BOTTOM" );
                    mPopupWindow.dismiss();
                }
            });
            pButton1.setText(sUpButton);
            pButton2.setText(sMiddleButton);
            pButton3.setText(sBottomButton);
            mPopupWindow = new PopupWindow(pwView, iWidth , iHeight );
            mPopupWindow.setOutsideTouchable(true);//當使用者按超出 PopupWindow 的範圍時， 事件能夠放PopupWindow外的 View 所接收，並且PopupWindow會被關上
            mPopupWindow.setFocusable(true) ;//設定Focus為true，讓Android知道應該要將焦點擺在PopupWindow上。不然執行PopupWindow內的程式碼，可運作，但點擊時沒有反應，也就是沒有變色；
            //
            mPopupWindow.showAtLocation(pScreenView, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * Unity call android method to get verison name and verison code.
     */
    public void androidSDKVersion()
    {
        UnityPlayer.UnitySendMessage("Main Camera", "UnityGetAndroidSDKVersionName", sVersionName);
        UnityPlayer.UnitySendMessage("Main Camera", "UnityGetAndroidSDKVersionCode", sVersionCode );
    }

    /**
     * android method to get verison name and verison code.
     * @param context
     * @return
     */
    public PackageInfo getVersionInfo(Context context)
    {
        try
        {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pinfo ;
        }
        catch (android.content.pm.PackageManager.NameNotFoundException e)
        {
            return null;
        }
    }
}
