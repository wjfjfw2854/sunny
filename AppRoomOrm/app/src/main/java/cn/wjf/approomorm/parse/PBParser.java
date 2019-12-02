package cn.wjf.approomorm.parse;

import com.google.protobuf.nano.MessageNano;

import cn.wjf.approomorm.nano.BaseResponse;
import cn.wjf.approomorm.network.BusinessPackage;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class PBParser<T extends MessageNano> implements Function<BusinessPackage, Observable<BusinessPackage<T>>> {
    private Class<T> t;

    public PBParser(Class<T> tClass) {
        this.t = tClass;
    }

//    @Override
//    public Observable<BusinessPackage<T>> call(BusinessPackage tBusinessPackage) {
//        BusinessPackage<T> businessPackage = new BusinessPackage<>();
//        try {
//            final BaseResponse.Base_Response baseResponse = BaseResponse.Base_Response.parseFrom(tBusinessPackage.getMsgData());
//            if (baseResponse.result.getCode() == 0) {
//                final T obj = T.mergeFrom(t.newInstance(), baseResponse.detail.getValue());
//                businessPackage.setResEntity(obj);
//                businessPackage.setRequestTag(tBusinessPackage.getRequestTag());
//            }
//            businessPackage.setRetMsg(baseResponse.result.getMsg());
//            businessPackage.setRetCode(baseResponse.result.getCode());
//        } catch (Exception e) {
//            businessPackage.setRetMsg(e.getMessage());
//            e.printStackTrace();
//        }
//        return Observable.just(businessPackage);
//    }

    @Override
    public Observable<BusinessPackage<T>> apply(BusinessPackage tBusinessPackage) throws Exception {
        BusinessPackage<T> businessPackage = new BusinessPackage<>();
        try {
            final BaseResponse.Base_Response baseResponse = BaseResponse.Base_Response.parseFrom(tBusinessPackage.getMsgData());
            if (baseResponse.result.getCode() == 0) {
                final T obj = T.mergeFrom(t.newInstance(), baseResponse.detail.getValue());
                businessPackage.setResEntity(obj);
                businessPackage.setRequestTag(tBusinessPackage.getRequestTag());
            }
            businessPackage.setRetMsg(baseResponse.result.getMsg());
            businessPackage.setRetCode(baseResponse.result.getCode());
        } catch (Exception e) {
            businessPackage.setRetMsg(e.getMessage());
            e.printStackTrace();
        }
        return Observable.just(businessPackage);
    }
}
