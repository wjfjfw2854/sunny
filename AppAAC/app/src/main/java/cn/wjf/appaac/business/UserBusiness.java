package cn.wjf.appaac.business;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.wjf.appaac.datas.User;

public class UserBusiness {

    private String nickName;
    private int age;
    private User user;
    private static UserBusiness userBusiness;

    private List<WeakReference<UserListener>> lis = new ArrayList<>();

    public static UserBusiness get()
    {
        return userBusiness == null ? userBusiness = new UserBusiness() : userBusiness;
    }

    public void addListener(UserListener userListener)
    {
        if(lis.size() > 10)
        {
            lis.remove(0);
        }
        WeakReference<UserListener> l = new WeakReference<UserListener>(userListener);
        lis.add(l);
    }

    public void removeListener(UserListener userListener)
    {
        int indexLis = 0;
        for(int i = 0;i < lis.size();i ++)
        {
            WeakReference<UserListener> weakReference = lis.get(i);
            UserListener userL = weakReference.get();
            if(userL.equals(userListener))
            {
                indexLis = i;
                break;
            }
        }
        lis.remove(indexLis);
    }

    public void requestUser()
    {
        String name = "扑棣奇";
        age ++;
        if(age > 30)
        {
            age = 0;
        }
        name += "_" + age;
        nickName = name;
        if(lis != null && lis.size() > 0)
        {
            User user = getUser();
            user.name = nickName;
            user.age = String.valueOf(age);
            for(int i = 0;i < lis.size();i ++)
            {
                WeakReference<UserListener> userListenerWeak = lis.get(i);
                UserListener userListener = userListenerWeak.get();
                if(userListener != null)
                {
                    userListener.onRequestUserResult(0,user);
                }
            }
        }
    }

    public User getUser()
    {
        return user == null? user = new User() : user;
    }

    public interface UserListener{
        public void onRequestUserResult(int code,User user);
    }
}
