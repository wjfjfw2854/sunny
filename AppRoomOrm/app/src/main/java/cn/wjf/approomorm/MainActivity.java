package cn.wjf.approomorm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import java.util.List;

import cn.wjf.approomorm.adapter.RvAdapter;
import cn.wjf.approomorm.base.BaseActivity;
import cn.wjf.approomorm.buinesses.StockDbUpdate;
import cn.wjf.approomorm.databinding.ActivityMainBinding;
import cn.wjf.approomorm.db.AppDataBase;
import cn.wjf.approomorm.db.MyDao;
import cn.wjf.approomorm.db.MyData;
import cn.wjf.approomorm.db.stockdb.StockList;
import cn.wjf.approomorm.db.stockdb.StockListDataBase;
import cn.wjf.approomorm.vm.MainVm;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding db;
    private AppDataBase appDataBase;
    private RvAdapter adapter;
    private MainVm vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm = new MainVm();
        creatDbByThread();
        initV();
        initRv();
    }

    private void initRv() {
        db.rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RvAdapter();
        db.rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initV() {
        db.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observ();
            }
        });
        db.butSl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observer observer = new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        if(o == null)
                        {
                            return;
                        }
                        if(!(o instanceof List) || ((List<StockList>)o).size() == 0)
                        {
                            return;
                        }
                        final List<StockList> lists = (List<StockList>)o;

                        String name = db.butSl.getText().toString();
                        db.butSl.setText(name + "共" + lists.size() + "条");
                        adapter.lists.clear();
                        adapter.lists.addAll(lists);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                StockDbUpdate stockDbUpdate = new StockDbUpdate();
                stockDbUpdate.requestGoodsTable(observer);
            }
        });

        db.butQueryByCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryObservable();
            }
        });
        db.butJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RankActivity.class);
                startActivity(intent);
            }
        });
    }

    private void observ() {
        ObservableOnSubscribe observableOnSubscribe = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                StringBuffer stringBuffer = getDao();
                e.onNext(stringBuffer.toString());
            }
        };
        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        db.txt.setText((String)o);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private StringBuffer getDao() {
        MyDao dao = appDataBase.Dao();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("以下是数据内容----" + "\n");
        List<MyData> datas = dao.getAll();
        int k = -1;
        for(MyData data : datas)
        {
            k ++;
            stringBuffer.append("\n" + "记录第" + k + "条----" + "\n");
            stringBuffer.append("id: " + data.idX + "\n");
            stringBuffer.append("name: " + data.nameX + "\n");
            stringBuffer.append("content: " + data.contentX + "\n");
        }
        return stringBuffer;
    }

    private void creatDbByThread() {
        ObservableOnSubscribe observableOnSubscribe = new ObservableOnSubscribe(){
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                creatDb();
            }
        };
        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void creatDb()
    {
        appDataBase = AppDataBase.getDb(MainActivity.this);
        MyDao myDao = appDataBase.Dao();
        int size = 10;
        MyData[] mdatas = new MyData[size];
        for(int i = 0;i < size;i ++)
        {
            MyData myData = new MyData();
            myData.idX = 1 + i;
            myData.nameX = "寄语？_" + i;
            myData.contentX = "不失颜值微笑_" + i;
            mdatas[i] = myData;
        }
        myDao.inser(mdatas);
    }

    public void queryObservable()
    {
        ObservableOnSubscribe observableOnSubscribe = new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                List<StockList> list = StockListDataBase.instance.stockListDao().getByGoodId(60031);
                e.onNext(list);
            }
        };
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                if(o == null || !(o instanceof List))
                {
                    return;
                }
                List<StockList> lists = (List<StockList>)o;
                String name = db.butQueryByCode.getText().toString();
                db.butQueryByCode.setText(name + "搜索到" + lists.size() + "条");
                adapter.lists.clear();
                adapter.lists.addAll(lists);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
