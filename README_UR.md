# KIKO AI — Android Project (Phase 1 foundation)

یہ Android Studio / GitHub Actions کے لیے Java پر مبنی پروجیکٹ ہے۔ اسے مکمل تجارتی Jarvis یا مکمل خودکار AI سمجھ کر پیش نہیں کیا جا رہا۔ یہ قابلِ تعمیر بنیاد ہے جس میں بنیادی voice input، اردو TTS، چند مقامی commands، Teacher کے ابتدائی اسباق، permission settings، Accessibility service skeleton اور feature-request center شامل ہیں۔

## آپ کے فون کے لیے
- ہدف: Android 15، 4GB اصل RAM، 64GB اسٹوریج۔
- 8GB RAM Plus ورچوئل/extended memory ہے؛ اسے 12GB حقیقی RAM نہ سمجھیں۔
- minSdk 26، targetSdk 35، Java 17، Android Gradle Plugin 8.7.3۔
- ایپ کو ہلکا رکھنے کے لیے اس مرحلے میں کوئی بڑا مقامی AI model شامل نہیں۔

## ابھی موجود فیچرز
1. اردو voice recognition (فون کے speech recognition provider پر منحصر)۔
2. Android Text-to-Speech؛ آواز اور زبان فون کی TTS settings سے بدلیں۔
3. Camera intent، YouTube/browser، music app launch، alarm screen، time، message compose screen۔
4. Teacher کے ابتدائی offline lessons اور ایک سادہ quiz۔
5. Accessibility service skeleton: Back/Home اور visible text پر click helper۔ اسے صارف کو Android Settings سے خود فعال کرنا ہوگا۔
6. Feature request محفوظ کرنا اور آخری درخواست دیکھنا۔
7. GitHub Actions workflow سے debug APK بنانا۔

## ابھی مکمل نہیں / اگلے مراحل
- Cloud AI tutor اور API backend۔
- قابلِ اعتماد code generation، isolated build/test sandbox، static analysis اور automated test suite۔
- حقیقی feature branch generation، code diff review، backup/rollback، signed release pipeline۔
- update server، version detection، APK signature verification اور in-app update flow۔
- MediaStore file search/download manager، reminders database، scheduled notifications۔
- accessibility کے ذریعے عمومی UI automation کی مکمل command parser۔
- multilingual Urdu/English/Arabic conversation management۔
یہ سب الگ الگ محفوظ مراحل میں implement اور device پر test ہونے چاہئیں۔

## GitHub پر APK بنانے کے مراحل
1. ZIP کو extract کریں۔
2. GitHub پر نیا repository بنائیں۔
3. Extract کیے ہوئے فولڈر کے اندر کی تمام فائلیں repository میں upload کریں (صرف ZIP فائل upload کرنے سے build نہیں ہوگا)۔
4. `Commit changes` کریں۔
5. GitHub repository میں **Actions** کھولیں۔
6. `Build KIKO AI APK` workflow منتخب کریں اور `Run workflow` دبائیں (یا main پر push کریں)۔
7. Build مکمل ہونے پر workflow run کھولیں، نیچے **Artifacts** میں `KIKO-AI-debug-apk` ڈاؤن لوڈ کریں۔
8. ZIP artifact extract کریں؛ `app-debug.apk` انسٹال کریں۔ Android نامعلوم ذرائع سے انسٹال کرنے کی اجازت مانگ سکتا ہے۔

## Android پر ضروری اجازتیں
- مائیک: voice recognition کے لیے۔
- Accessibility: صرف اضافی UI control کے لیے؛ Settings میں صارف خود فعال کرے۔
- Camera: camera app intent/feature کے مطابق Android permission prompt دے سکتا ہے۔
- Notifications / exact alarm: reminders اور alarms کے لیے مستقبل کے مرحلے میں۔
اجازت نہ دیں تو متعلقہ فیچر کام نہیں کرے گا۔ حساس کاموں میں KIKO کو آزادانہ اجازت نہ دیں۔

## حفاظتی اصول
- KIKO خود سے code execute یا خاموشی سے APK install نہیں کرتا۔
- فیچر تبدیلی کے لیے درست pipeline یہ ہونی چاہیے: user request → requirements → code diff → static checks → tests → backup → user approval → signed APK → Android install confirmation۔
- عام Android ایپ Android کی package installer confirmation کو bypass نہیں کر سکتی۔ ہر update پر OS دوبارہ confirmation دکھا سکتا ہے۔
- API keys کو APK یا GitHub repository میں hard-code نہ کریں۔ Public release کے لیے backend proxy استعمال کریں۔
- صرف اپنے devices، accounts اور اجازت یافتہ lab پر ethical hacking سیکھیں۔

## Build note
یہ پروجیکٹ اس ماحول میں Android SDK/Gradle کے ساتھ compile-test نہیں کیا گیا۔ GitHub Actions workflow اسی مقصد کے لیے شامل ہے؛ اگر build error آئے تو Actions کے log کا مکمل error حصہ شیئر کریں۔
