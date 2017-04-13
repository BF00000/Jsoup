package com.iceflame.query;

import java.io.Console;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ProgressDialog mpDialog;
	private Handler mHandler;
	private EditText et_name;
	private EditText et_id;
	private EditText et_costype;
	private EditText et_x;
	private EditText et_y;
	private Button btn_query;
	private TextView tv_display;
	private String id;
	private String name;
	private String costype;
	private String x;
	private String y;
	
	private String query_result;
	private String time;
	private String school;
	private String type;
	private String sum;


	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mHandler=new Handler(){
        	@Override
        	public void handleMessage(Message msg) {
        		tv_display.setText(query_result);
        	}
        	
        };
        
        initUI();
        initEvent();
    }

	private void initUI() {
		 et_name = (EditText) findViewById(R.id.et_name);
	     et_id = (EditText) findViewById(R.id.et_id);
	     et_costype = (EditText) findViewById(R.id.et_costype);
	     et_x = (EditText) findViewById(R.id.et_x);
	     et_y = (EditText) findViewById(R.id.et_y);
	     
	     btn_query=(Button)findViewById(R.id.btn_query);
	     tv_display=(TextView)findViewById(R.id.tv_display);
	}

	private void initEvent() {
		
		
		btn_query.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				id = et_id.getText().toString();
				name=et_name.getText().toString();
				costype=et_costype.getText().toString();
				x=et_x.getText().toString();
				y=et_y.getText().toString();
				
				
				if(isConnect(MainActivity.this)==true)
				{
//					if (id.length() == 15 && name.length() > 0) 
					if (id.length() == 14 && name.length() > 0) 
					{
						
						mpDialog = new ProgressDialog(MainActivity.this); // 设置风格为圆形进度条
						mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置提示信息
						mpDialog.setMessage("正在查询...");// 设置进度条是否为不明确
						mpDialog.setIndeterminate(false); // 设置进度条是否可以按退回键取消
						mpDialog.setCancelable(true); // 显示进度条
						mpDialog.show();
						
						new Thread(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									getScore(id,name);
//									getScore(id,name,costype,x,y);
									System.out.println("id:"+id);
									System.out.println("name:"+name);
									System.out.println("costype:"+costype);
									System.out.println("x:"+x);
									System.out.println("y:"+y);
									
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
							}).start();
						
						
						
					}else
					{
//						if(id.length()!=15)
						if(id.length()!=14)
						{
							Toast.makeText(MainActivity.this,"准考证格式出错",
									Toast.LENGTH_SHORT).show();
						}
						
						if(name.length()==0)
						{
							Toast.makeText(MainActivity.this,"请输入姓名",
									Toast.LENGTH_SHORT).show();
						}
						
					}
					
				}else
				{
					Toast.makeText(MainActivity.this,"网络连接异常",
						Toast.LENGTH_SHORT).show();
					
				}
				
			}

			public  boolean isConnect(Context context) {
				ConnectivityManager connectivity = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);

				if (connectivity != null) {
					// 获取网络连接管理的对象
					NetworkInfo info = connectivity.getActiveNetworkInfo();
					if (info != null && info.isConnected()) {
						// 判断当前网络是否已经连接
						if (info.getState() == NetworkInfo.State.CONNECTED) {
							Toast.makeText(MainActivity.this,"网络已连接",
									Toast.LENGTH_SHORT).show();
							return true;
						}
					}
				}
				return false;
			}
		
		});
		
	}
	

//	private void getScore(String id,String name,String costype,String x,String y) throws IOException {
		private void getScore(String id,String name) throws IOException {
		
//		Document doc = Jsoup.connect("http://www.chsi.com.cn/cet/query")
//				.data("zkzh", id).data("xm", name) // 请求参数
//				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0") // 设置 User-Agent
////				.cookie("auth", "token")// 设置 cookie
//				.cookie("JSESSIONID", "F352B5FC27171F5326C673E255C25A90")// 设置 cookie
//				.timeout(3000) // 设置连接超时时间
//				.header("Referer", "http://www.chsi.com.cn/cet/").get(); 
//		
//            System.out.println(doc.toString());
//        	parseScore(doc);
        	
        	costype="login";
        			Document doc = Jsoup.connect("http://202.118.120.103:81/main/center/index.asp?cb=1")
//        			Document doc = Jsoup.connect("http://202.118.120.103:81/")
//    				.data("user_uid", id).data("user_pwd", name).data("costype", costype).data("imageField.x", x).data("imageField.y", y) // 请求参数
    				.data("user_uid", id).data("user_pwd", name) // 请求参数
    				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0") // 设置 User-Agent
//    				.cookie("auth", "token")// 设置 cookie
    				.cookie("ASPSESSIONIDSCBCRDBB", "ICFLHFFAJELEOOJABEDIHLEB")
    				.cookie("ASPSESSIONIDSCBCRDBB", "LCFLHFFAEELNIMAPNGGPEBAF")// 设置 cookie
    				.timeout(3000) // 设置连接超时时间
//    				.header("Referer", "http://202.118.120.103:81/main/center/index.asp?cb=1").get(); 
        			.header("Referer", "http://202.118.120.103:81/").get(); 
//        			.header("Referer", "http://ges.lnpu.edu.cn/").get(); 
        	
    		
                System.out.println(doc.toString());
            	parseScore(doc);
	}

	private void parseScore(Document doc) {
//		Elements elements = doc.getElementsByTag("tr");
//		for(int i=0;i<elements.size();i++){
//			
//			if(elements.get(i).text() !=null){
//				System.out.println("scor"+elements.get(i).text());
//				sum=elements.get(i).text()+"\n";
//				query_result+=sum; 
//				System.out.println("22"+query_result);
//			}
//		}
		
////		Elements elements = doc.getElementsByTag("title");
		Elements elements = doc.getElementsByClass("user");
		for(int i=0;i<elements.size();i++){
			
			if(elements.get(i).text() !=null){
				System.out.println("scor"+elements.get(i).text());
				sum=elements.get(i).text()+"\n";
				query_result+=sum; 
				System.out.println("22"+query_result);
			}
		}
		mpDialog.cancel();
		mHandler.sendEmptyMessage(0);	
		
//		for (Element elementtwo : elements) {
//			
//			
////			query_result=elementtwo.getElementsByTag("th").text()+"**";
////			query_result+=elementtwo.getElementsByTag("tr").text();
//			
////			System.out.println("sixes"+elements.size());
//			System.out.println("tabs"+elementtwo.getElementsByTag("th").text());
////			System.out.println("\n");
//			System.out.println("scor"+elementtwo.getElementsByTag("tr").text());
//			
//			query_result="结果："+elementtwo.getElementsByTag("tr").text()+"\n";
//			sum+=query_result;
//			
//			
////			query_result="学校："+school+"\n类型："+type+"\n证号："+id+"\n时间："+time+"\n结果："+result;		
//			mpDialog.cancel();
//			mHandler.sendEmptyMessage(0);	
//			
////			if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.name_3))) {
////
////				name = elementtwo.getElementsByTag("td").text();
////				System.out.println("name="+name);
////			} else if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.school))) {
////
////				school = elementtwo.getElementsByTag("td").text();
////				System.out.println("school="+school);
////			} else if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.type))) {
////
////				type = elementtwo.getElementsByTag("td").text();
////				System.out.println("type="+type);
////			} else if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.id_2))) {
////
////				id = elementtwo.getElementsByTag("td").text();
////				System.out.println("id="+id);
////			} else if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.time))) {
////
////				time = elementtwo.getElementsByTag("td").text();
////				System.out.println("time="+time);
////			} else if (elementtwo.getElementsByTag("th").text()
////					.equals(getResources().getString(R.string.sum_2))) {
////
////				String result = elementtwo.getElementsByTag("td").text();
////				System.out.println("result="+result);
////
////				query_result="学校："+school+"\n类型："+type+"\n证号："+id+"\n时间："+time+"\n结果："+result;		
////				mpDialog.cancel();
////				mHandler.sendEmptyMessage(0);		
////				
////	         }
//		}
//		
	}
}
