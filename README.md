# camellia-android
Automatically exported from code.google.com/p/camellia-android
    from 2015/5/12

English

camellia-android is a Android JNI wrapper for an open source implementation of the Camellia encryption library. Currently, it only supports Ubuntu, but is based on the ctypes module, so should be easy to add support for Unix/Linux/Windows.
AESLib is a Android Java wrapper for an open source implementation of the AES encryption library.
Japanese

camellia-androidはCamellia（共通鍵暗号方式）のAndroid JNIラッパーとして開発しオープンソースとして公開します。現在のバージョンではUbuntuで開発を行っています。ライブラリのバイナリはどの環境でも簡単に利用出来ます。
AESLibはJavaラッパーとして開発しオープンソースとして公開します。
What's new(2011/8/2)
English

aeslib was bug fixed.

    bug: The same could not be encrypted AESLib different versions of the Android.
    bug: Parameter set to character type to getBytes() method.
    mod: Sets the salt key to the constructor of AESLib.
    test version: Android 2.1, 2.2, 2.3.1, 3.0

Japanese

aeslibのバグフィックス対応

    bug: 異なるAndroidバージョン間で暗号化結果が同一とならなかった。
    bug: getBytes()メソッドに文字種別を設定していなかった。
    mod: AESLibのコンストラクタに、Saltキーを設定できるように変更した。
    test対象: Android 2.1, 2.2, 2.3.1, 3.0

What's new(2011/7/27)
English

camellia-android was bug fixed.

    bug: If there is no ciphers key(case:CiphersKey? is null) does not output an exception error. 

aeslib is First release.

    aeslib will use the android framework and has camellia-android compatible interface.
    aeslib is camellia-android performance comparison purposes and was created.
    Therefore, we do not provide binaries.

Japanese

camellia-androidのバグフィクス対応

    bug: 暗号キーが無い(CiphersKey?がnull時)の場合、例外エラーが出力されません。

aeslibの初版リリース

    aeslibはandroidフレームワークを利用し、camellia-androidとインタフェース互換としています。
    これは、camellia-androidと性能比較用途で作成しました。
    したがって、aeslibはバイナリでの提供は行なっていません。

What's new(2011/5/31)
English

First release.
Japanese

初版リリース
camellia-android Specifications
English

    Android 2.1～ (Others not tested.)
    Encryption key bit length support 128 bit, 192 bit, 256 bit.
    Library password available for all characters.(UTF-8 string)
    Library is to use SHA256 hashed password.
    The password is not stored in a camellia-android.
    Encryption and decryption using the ArrayList<string>. Null data and synchronized position in List order.
    Recommend using encrypted data stored at SQLite or others if you want to store passwords​​.
    camellia-android will run faster than 3.5 times compared to the AES. 

Japanese

    Android 2.1以降（その他は未検証です）
    鍵長は128,192,256ビットをサポート．(camellia準拠)
    パスワードにはJava String文字列(utf-8)で利用できる全ての文字が利用可能．
    camelliaライブラリ内で、パスワードをSHA256化し利用しています．
    パスワードはcamellia-android内には保管しません．
    暗号化や復号化にはArrayList<String>を使用し、null値やList順序も同期．
    SQLite等でパスワード保管する場合に暗号化使用を推奨します．
    camellia-androidはAESと比較して3.5倍以上高速に動作します。 
