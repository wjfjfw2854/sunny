package cn.wjf.approomorm.db;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class SQLiteHelper {
    public static final String DATABASE_NAME = "databasewjf_v1";
    public static final String ASSET_DB_PATH = "database";

    public String archivePath;//压缩包路径
    public String dataBasePath;//存入文件夹路径
    public Context context;

    public SQLiteHelper(Context context)
    {
        this.context = context;
        archivePath = ASSET_DB_PATH + "/" + DATABASE_NAME + ".zip";
        dataBasePath = context.getApplicationInfo().dataDir + "/" + ASSET_DB_PATH;
    }

    public void copyAssetZip2PathAssign()
    {
        if(context == null)
        {
            return;
        }
        try{
            InputStream dbZipStream = context.getAssets().open(archivePath);
            File file = new File(dataBasePath + "/");
            if(!file.exists())
            {
                file.mkdir();
            }
            ZipInputStream zipInputStream = new ZipInputStream(dbZipStream);
            if(zipInputStream.getNextEntry() != null)
            {
                FileOutputStream fileOutputStream = new FileOutputStream(dataBasePath + "/" + DATABASE_NAME);
                byte[] buf = new byte[1024];
                int len;
                while ((len = zipInputStream.read(buf)) > 0)
                {
                    fileOutputStream.write(buf,0,len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                zipInputStream.close();
            }
        }
        catch (Exception e)
        {

        }
    }

    public AppDataBase openDb()
    {
        return AppDataBase.getDb(context);
    }
}
