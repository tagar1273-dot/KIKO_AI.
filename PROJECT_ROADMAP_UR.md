# KIKO AI Roadmap

## Phase 1 — Foundation (اس ZIP میں)
App shell، voice input، TTS، بنیادی commands، Teacher lesson screen، permission settings، accessibility skeleton، GitHub build workflow۔

## Phase 2 — Reliable phone assistant
Command intent parser، app launch by package, file search using scoped MediaStore permissions، alarms/reminders database، download manager، camera capture flow، SMS draft with explicit send approval۔

## Phase 3 — AI Teacher + Cloud AI
Backend proxy، Urdu/English/Arabic tutoring، lesson plans، code explanations، quizzes، progress tracking۔ API secrets صرف backend میں۔

## Phase 4 — Code lab
Project workspace، generated diff only، no arbitrary execution، isolated CI build, lint/static analysis, unit tests, dependency checks, test report۔

## Phase 5 — Safe self-upgrade
Version manifest، signed APK/AAB release, signature/hash verification، backup/rollback، update history، user approval، Android package installer confirmation۔

## Acceptance rules
- ہر permission واضح مقصد کے ساتھ۔
- حساس کام سے پہلے confirmation۔
- generated code پہلے review اور tests سے گزرے۔
- failure پر پرانا stable version برقرار رہے۔
- کوئی خاموش installation یا permission bypass نہیں۔
