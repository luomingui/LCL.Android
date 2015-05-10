android-app
===========

# **���¿Ƽ� Android �ͻ�����Ŀ����** #

*ע�����ļ������Ѿ���Android��������*

����Eclipse������˵�������Android�ͻ�����Ŀ����ȷ���㵱ǰ��Android SDK�����°档<br>
�������������޸���Ŀ��Ŀ¼�µ� project.properties �ļ���<br>
�Ƽ�ʹ��Android 4.0 ���ϰ汾��SDK,��ʹ��JDK1.6���룺

> target=android-15

**��ӭ�������������Ͻ��иĽ��������ҷ���**

���潫�򵥵Ľ�������Ŀ��

## **һ����Ŀ��Ŀ¼�ṹ** ##
> ��Ŀ¼<br>
> �� src<br>
> �� libs<br>
> �� res<br>
> �� AndroidManifest.xml<br>
> �� project.properties<br>

**1��srcĿ¼**<br>
srcĿ¼���ڴ����Ŀ�İ���javaԴ���ļ���

������srcĿ¼����Ŀ¼��
src<br>
lcl.android.activity<br> �� APP�����
lcl.android.adapter<br>  �� APP�б���������
lcl.android.api<br> �� API���ʰ�
lcl.android.control<br>�� APP�ؼ���
lcl.android.core<br> �� APP�����������
lcl.android.fragment<br>�� APP�����
lcl.android.mail<br>
lcl.android.rss<br>
lcl.android.sms<br>
lcl.android.test<br>
lcl.android.utility<br> �� APP���߰�

- com.weibo.net �� ����΢��SDKԴ���
- greendroid.widget �� ��ݲ˵������(����UI��[GreenDroid](http://www.oschina.net/p/greendroid))
- net.oschina.app �� APP�����������
- net.oschina.app.adapter �� APP�б���������
- net.oschina.app.api �� API���ʰ�
- net.oschina.app.bean �� APPʵ���
- net.oschina.app.common �� APP���߰�
- net.oschina.app.ui �� APP�����
- net.oschina.app.widget �� APP�ؼ���


**2��libsĿ¼**<br>
libsĿ¼���ڴ����Ŀ���õ���jar���ļ���

������libsĿ¼���jar���ļ���
> libs<br>
> �� commons-httpclient-3.1.jar<br>

- commons-httpclient-3.1.jar �� Apache��HttpClient��

**3��resĿ¼**<br>
resĿ¼���ڴ����Ŀ��ͼƬ�����֡���ʽ����Դ�ļ���

������resĿ¼����Ŀ¼��
> res<br>
> �� anim<br>
> �� color<br>
> �� drawable<br>
> �� drawable-hdpi<br>
> �� drawable-ldpi<br>
> �� drawable-mdpi<br>
> �� layout<br>
> �� menu<br>
> �� raw<br>
> �� values<br>
> �� xml<br>

- anim �� ����Ч��
- color �� ��ɫ
- drawable/drawable-hdpi/drawable-ldpi/drawable-mdpi �� ͼ�ꡢͼƬ
- layout �� ���沼��
- menu �� �˵�
- raw �� ֪ͨ��
- values �� ���԰�����ʽ
- xml �� ϵͳ����

**4��AndroidManifest.xml**<br>
AndroidManifest.xml��������Ӧ�ó���İ汾�����⡢�û�Ȩ�޼�ע��Activity�ȡ�

## **������Ŀ�Ĺ�������** ##

#### 1��APP�������� ####
AndroidManifest.xmlע�����������Ϊ"AppStart"�������ļ�Ϊnet.oschina.app\AppStart.java�ļ���������ʾ��ӭ����֮��ͨ����ͼ(Intent)��ת����ҳ��net.oschina.app.ui\Main.java����<br>
*ע������������֮�⣬�������н��涼����src\net.oschina.app.ui���С�*

#### 2��APP����API���� ####

����ҳ��Ѷ�б���ʾ����API����Ϊ����

**1) ��ʼ���ؼ�**<br>
��ҳActivity(Main.java)��onCreate()����������ز����ļ�(Main.xml)��������ˢ���б�ؼ�(PullToRefreshListView)�����˳�ʼ����������������������(ListViewNewsAdapter)��<br>
*ע��Main.xml�����ļ���res\layoutĿ¼�£�PullToRefreshListView�ؼ���net.oschina.app.widget����ListViewNewsAdapter��������net.oschina.app.adapter����*

**2) �첽�̷߳���**<br>
�б�ؼ���ʼ���󣬿���һ���̷߳���(loadLvNewsData())���÷����е���ȫ��Ӧ�ó�����(AppContext)������API�ͻ�����(ApiClient)��ͨ��ApiClient��http��ʽ�����������API��������Ӧ��XML���ݣ���ͨ��ʵ��Bean(NewsList)����XML������ʵ��(NewsList)��UI�ؼ�(PullToRefreshListView)չʾ��<br>
*ע��AppContextȫ��Ӧ�ó�������net.oschina.app����ApiClient API�ͻ�������net.oschina.app.api����*

**3) ����������ʾ**<br>
����õ����󣬽����ض�Ӧ����ѶXML���ݣ���ͨ����Ѷʵ����(NewsList)����XML������ʵ��(NewsList)��UI�ؼ�(PullToRefreshListView)չʾ��<br>
*ע��NewsListʵ������net.oschina.app.bean����*