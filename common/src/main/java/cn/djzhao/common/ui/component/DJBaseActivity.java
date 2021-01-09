package cn.djzhao.common.ui.component;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础Activity
 *
 * @author djzhao
 * @date 21/01/09
 */
public class DJBaseActivity extends AppCompatActivity implements DJBaseActionInterface {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
