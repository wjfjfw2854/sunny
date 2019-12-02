package cn.wjf.approomorm.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.ObservableField;

import java.util.HashMap;
import java.util.Map;


public class Goods implements Cloneable, Parcelable {

    public static final Goods GOODS_SH_INDEX = new Goods(1, "上证指数", "000001", 0, 1);
    public static final Goods GOODS_SZ_INDEX = new Goods(1399001, "深证成指", "399001", 1, 1);
    public static final Goods GOODS_ZXB_INDEX = new Goods(1399005, "中小板指", "399005", 1, 1);
    public static final Goods GOODS_CHUANG_YE_INDEX = new Goods(1399006, "创业板指", "399006", 1, 1);
    public static final Goods GOODS_HS300_INDEX = new Goods(300, "沪深300", "000300", 0, 1);
    public static final Goods GOODS_HSI = new Goods(5500001, "恒生指数", "HSI", 5, 33);
    public static final Goods GOODS_NASDAQ = new Goods(6703002, "纳斯达克指数", "NASDAQ", 6703, 1);
    public static final Goods GOODS_DJI = new Goods(6703004, "道琼斯指数", "DJIA", 6703, 1);

    public static final String SUSPENSION_FLAG = "1";//停牌标记，1：停牌，0：正常

    private int goodsId;
    public ObservableField<String> goodsName = new ObservableField<>("");
    public ObservableField<String> goodsCode = new ObservableField<>("");

    private String mPositionAmount = "0";
    private String mPositionPrice = "0.00";// 设置持仓成本价

    public int exchange = -1;//股票市场
    public long category;//股票类型
    private Map<Integer, String> mGoodsValue = new HashMap<>();

    public Goods() {
    }

    public Goods(int id) {
        this(id, "");
    }

    public Goods(int id, String name) {
        goodsId = id;
        setValue(GoodsParams.STOCK_NAME, name);
    }

    public Goods(int id, String name, String code) {
        this(id, name);
        setValue(GoodsParams.STOCK_CODE_NAME, code);
    }

    public Goods(int id, String name, String code, int exchange, long category) {
        this(id, name, code);
        this.exchange = exchange;
        this.category = category;
    }

    protected Goods(Parcel in) {
        goodsId = in.readInt();
        goodsName.set(in.readString());
        goodsCode.set(in.readString());
        exchange = in.readInt();
        category = in.readLong();
        mGoodsValue = in.readHashMap(Goods.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(goodsId);
        dest.writeString(goodsName.get());
        dest.writeString(goodsCode.get());
        dest.writeInt(exchange);
        dest.writeLong(category);
        dest.writeMap(mGoodsValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public int getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName.get();
    }

    public String getGoodsCode() {
        return goodsCode.get();
    }

    public int getExchange() {
        return exchange;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    @Override
    public Goods clone() {
        try {
            Goods goods = (Goods) super.clone();
            goods.mGoodsValue = new HashMap<>();
            for (Map.Entry<Integer, String> entry : mGoodsValue.entrySet()) {
                goods.setValue(entry.getKey(), entry.getValue());
            }
            return goods;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getValue(int goodsParam) {
        return mGoodsValue.get(Integer.valueOf(goodsParam));
    }

    public void setValue(int goodsParam, String value) {
        mGoodsValue.put(Integer.valueOf(goodsParam), value);

        switch (goodsParam) {
            case GoodsParams.STOCK_NAME:
                goodsName.set(value);
                break;
            case GoodsParams.STOCK_CODE_NAME:
                goodsCode.set(value);
                break;
        }
    }

    public void setPositionAmount(String amount) {
        mPositionAmount = amount;
    }

    public String getPositionAmount() {
        return mPositionAmount;
    }

    /**
     * 设置持仓成本价
     */
    public void setPositionPrice(String price) {
        mPositionPrice = price;
    }

    public String getPositionPrice() {
        return mPositionPrice;
    }

    /**
     * 是否停牌
     *
     * @return
     */
    public boolean isSuspension() {
        return SUSPENSION_FLAG.equals(getValue(GoodsParams.CLO_PRC_BF4));
    }
}
