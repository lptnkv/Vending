package com.example.vending;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button bStart, btJustDoIt;
    TextView text;
    ProgressBar progressBar;
    Automat automat1, automat2, automat3, automat4;
    TextView status1;
    TextView price1;
    LinearLayout snacks1;
    LinearLayout order1;
    int[] r_status;
    int[] r_snacks;
    int[] r_order;
    int[] r_price;
    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        r_status = new int[]{R.id.status_1, R.id.status_2, R.id.status_3, R.id.status_4};
        r_snacks = new int[]{R.id.snacks_1, R.id.snacks_2, R.id.snacks_3, R.id.snacks_4};
        r_order = new int[]{R.id.order_1, R.id.order_2, R.id.order_3, R.id.order_4};
        r_price = new int[]{R.id.price_1, R.id.price_2, R.id.price_3, R.id.price_4};
        initAutomats();
        updateViews();

        MyTask mt1 = new MyTask(automat1);
        MyTask mt2 = new MyTask(automat2);
        mt1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        mt2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initAutomats(){
        automat1 = new Automat(1);
        automat2 = new Automat(2);
        automat3 = new Automat(3);
        automat4 = new Automat(4);

        for (int i = 0; i < 20; i++){
            automat1.addProduct(new DeliveryChips().createProduct());
            automat1.addProduct(new DeliveryWater().createProduct());
            automat1.addProduct(new DeliveryTwix().createProduct());

            automat2.addProduct(new DeliveryWater().createProduct());
            automat2.addProduct(new DeliveryRedBull().createProduct());
            automat2.addProduct(new DeliveryTwix().createProduct());

            automat3.addProduct(new DeliveryWater().createProduct());
            automat3.addProduct(new DeliveryTwix().createProduct());
            automat3.addProduct(new DeliveryNuts().createProduct());

            automat4.addProduct(new DeliveryWater().createProduct());
            automat4.addProduct(new DeliveryRedBull().createProduct());
            automat4.addProduct(new DeliveryNuts().createProduct());
        }
    }

    private void updateViews(){
        updateAutomat(automat1);
        updateAutomat(automat2);
        updateAutomat(automat3);
        updateAutomat(automat4);
    }

    private void updateAutomat(Automat automat){
        int id = automat.id;
        status1 = (TextView) findViewById(r_status[id-1]);
        order1 = (LinearLayout) findViewById(r_order[id-1]);
        order1.removeAllViews();

        snacks1 = (LinearLayout) findViewById(r_snacks[id-1]);
        snacks1.removeAllViews();

        TreeSet<IProduct> snackList1 = new TreeSet<>(automat.snacks.keySet());
        for (IProduct product : snackList1){
            String name = product.getName();
            double price = product.getPrice();
            int amount = automat.snacks.get(product);
            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams infoParams = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView nameView = new TextView(this);
            TextView priceView = new TextView(this);
            TextView amountView = new TextView(this);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            textParams.weight = 1;
            //textParams.gravity = Gravity.CENTER;

            nameView.setText(name);
            nameView.setLayoutParams(textParams);
            priceView.setText(Double.toString(price));
            priceView.setLayoutParams(textParams);
            amountView.setText(Integer.toString(amount));
            amountView.setLayoutParams(textParams);

            info.addView(nameView);
            info.addView(priceView);
            info.addView(amountView);
            snacks1.addView(info);
        }

        TreeSet<IProduct> orderList1 = new TreeSet<>(automat.order.keySet());
        for(IProduct product : orderList1){
            String name = product.getName();
            double price = product.getPrice();
            int amount = automat.order.get(product);
            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams infoParams = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView nameView = new TextView(this);
            TextView priceView = new TextView(this);
            TextView amountView = new TextView(this);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            textParams.weight = 1;
            nameView.setText(name);
            nameView.setLayoutParams(textParams);
            priceView.setText(Double.toString(price));
            priceView.setLayoutParams(textParams);
            amountView.setText(Integer.toString(amount));
            amountView.setLayoutParams(textParams);

            info.addView(nameView);
            info.addView(priceView);
            info.addView(amountView);
            order1.addView(info);
        }

        price1 = (TextView) findViewById(r_price[id-1]);
        price1.setText(Double.toString(automat.orderPrice));
    }

    class MyTask extends AsyncTask<Void, Void, Void>{
        private int id;
        private Automat automat;
        public MyTask(Automat automat){
            super();
            this.automat = automat;
            this.id = automat.id - 1;
        }

        @Override
        protected void onPreExecute(){
            ((TextView) findViewById(r_status[id])).setText("Покупка");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000) + 500);
                automat.addToOrder(new DeliveryWater().createProduct());
                publishProgress();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            try{
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000) + 200);
                automat.addToOrder(new DeliveryTwix().createProduct());
                publishProgress();
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1500) + 200);
                automat.addToOrder(new DeliveryTwix().createProduct());
                publishProgress();
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1500) + 500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            automat.payOrder();
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params){
            super.onProgressUpdate();
            updateViews();
        }

    }

}
