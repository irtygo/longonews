# Longo News

**Longo News** is a minimalist Android app that listens for ADB broadcast intents and shows notifications based on what you send it. It doesn’t verify or filter content — real, fake, or joke news, it all gets notified.

## Disclaimer

**Do not use Longo News to defraud, mislead, or harm people.**  
Use responsibly and ethically.

---

## Usage

Send a notification via ADB like this:

```bash
adb shell am broadcast \
-n com.longo.news/.NotificationReceiver \
-a com.longo.SHOW_NOTIFICATION \
--es title "Sony\ Announces\ PS6" \
--es text "Sony\ set\ the\ date\ for\ the\ PS6\ release\ date\ to\ June\ 14\ 2030." \
--es prio "high"
