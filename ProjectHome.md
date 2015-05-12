# **camellia-android** #
## English ##

camellia-android is a Android JNI wrapper for an open source implementation of the Camellia encryption library. Currently, it only supports Ubuntu, but is based on the ctypes module, so should be easy to add support for Unix/Linux/Windows.<br>
AESLib is a Android Java wrapper for an open source implementation of the AES encryption library.<br>
<br>
<h2>Japanese</h2>

camellia-androidはCamellia（共通鍵暗号方式）のAndroid JNIラッパーとして開発しオープンソースとして公開します。現在のバージョンではUbuntuで開発を行っています。ライブラリのバイナリはどの環境でも簡単に利用出来ます。<br>
AESLibはJavaラッパーとして開発しオープンソースとして公開します。<br>
<br>
<hr />

<h2><b>What's new(2011/8/2)</b></h2>
<h3>English</h3>
aeslib was bug fixed.<br>
<blockquote>bug: The same could not be encrypted AESLib different versions of the Android.<br>
bug: Parameter set to character type to getBytes() method.<br>
mod: Sets the salt key to the constructor of AESLib.<br>
test version: Android 2.1, 2.2, 2.3.1, 3.0<br></blockquote>

<h3>Japanese</h3>
aeslibのバグフィックス対応<br>
<blockquote>bug: 異なるAndroidバージョン間で暗号化結果が同一とならなかった。<br>
bug: getBytes()メソッドに文字種別を設定していなかった。<br>
mod: AESLibのコンストラクタに、Saltキーを設定できるように変更した。<br>
test対象: Android 2.1, 2.2, 2.3.1, 3.0<br></blockquote>


<h2><b>What's new(2011/7/27)</b></h2>
<h3>English</h3>
camellia-android was bug fixed.<br>
<blockquote>bug: If there is no ciphers key(case:CiphersKey is null) does not output an exception error.</blockquote>

aeslib is First release.<br>
<blockquote>aeslib will use the android framework and has camellia-android compatible interface.<br>
aeslib is camellia-android performance comparison purposes and was created.<br>
Therefore, we do not provide binaries.<br></blockquote>

<h3>Japanese</h3>
camellia-androidのバグフィクス対応<br>
<blockquote>bug: 暗号キーが無い(CiphersKeyがnull時)の場合、例外エラーが出力されません。<br></blockquote>

aeslibの初版リリース<br>
<blockquote>aeslibはandroidフレームワークを利用し、camellia-androidとインタフェース互換としています。<br>
これは、camellia-androidと性能比較用途で作成しました。<br>
したがって、aeslibはバイナリでの提供は行なっていません。<br></blockquote>


<h2><b>What's new(2011/5/31)</b></h2>
<h3>English</h3>
First release.<br>
<br>
<h3>Japanese</h3>
初版リリース<br>
<br>
<hr />
<h2><b>camellia-android Specifications</b></h2>
<h3>English</h3>
<ul><li>Android 2.1～ (Others not tested.)<br>
</li><li>Encryption key bit length support 128 bit, 192 bit, 256 bit.<br>
</li><li>Library password available for all characters.(UTF-8 string)<br>
</li><li>Library is to use SHA256 hashed password.<br>
</li><li>The password is not stored in a camellia-android.<br>
</li><li>Encryption and decryption using the <code>ArrayList&lt;string&gt;</code>. Null data and synchronized position in List order.<br>
</li><li>Recommend using encrypted data stored at SQLite or others if you want to store passwords​​.<br>
</li><li>camellia-android will run faster than 3.5 times compared to the AES.<br>
<h3>Japanese</h3>
</li><li>Android 2.1以降（その他は未検証です）<br>
</li><li>鍵長は128,192,256ビットをサポート．(camellia準拠)<br>
</li><li>パスワードにはJava String文字列(utf-8)で利用できる全ての文字が利用可能．<br>
</li><li>camelliaライブラリ内で、パスワードをSHA256化し利用しています．<br>
</li><li>パスワードはcamellia-android内には保管しません．<br>
</li><li>暗号化や復号化には<code>ArrayList&lt;String&gt;</code>を使用し、null値やList順序も同期．<br>
</li><li>SQLite等でパスワード保管する場合に暗号化使用を推奨します．<br>
</li><li>camellia-androidはAESと比較して3.5倍以上高速に動作します。