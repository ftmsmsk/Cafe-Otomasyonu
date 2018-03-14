


public class MyValidator {

    public  int adSoyadValidator(String kelime){
        for (char harf : kelime.toCharArray()) {
            if (!Character.isLetter(harf) && !Character.isWhitespace(harf)) {
                return -1; 
            }
        }
        return 1;
    }

    private String adSoyadHazirla(String kelime) {
        return kelime.substring(0, 1).toUpperCase() + kelime.substring(1).toLowerCase();
    }

    private  String adSoyadHazirla(String[] kelime) {
        String hazirla = "";
        for (String string : kelime) {
            hazirla += adSoyadHazirla(string) + " ";
        }
        return hazirla.trim();
    }

    public  int tcknValidator(String tckn)  {
        if (tckn.length() != 11) {
            //throw new Exception("TCKN 11 haneli olmalıdır");
            return -1;
        }
        for (char harf : tckn.toCharArray()) {
            if (!Character.isDigit(harf)) {
              //  throw new Exception("TCKN de sadece rakamlar bulunmalıdır");
              return -2;
            }
        }
        if (Integer.valueOf(tckn.charAt(0)) == 0 || Integer.valueOf(tckn.charAt(10)) % 2 != 0) {
           // throw new Exception("TCKN'niz yasadışıdır!");
           return -3;
        }
        int toplam1 = 0, toplam2 = 0;
        for (int i = 0; i < 11; i++) {
            if (i % 2 == 0) {
                toplam1 += Integer.valueOf(tckn.charAt(i));
            } else {
                toplam2 += Integer.valueOf(tckn.charAt(i));
            }
        }
        if (toplam1 % 10 != toplam2 % 10) {
           // throw new Exception("TCKN'Niz kuralsızdır");
        }
        return 1;
    }

    public  int emailValidator(String email) {
        if (!email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
            return -1;
        }
        return 1;
    }

    public  int telefonValidator(String tel) {
        if (tel.length() != 10) {
          //  throw new Exception("Telefon numarası 10 haneli olmalıdır");
          return -2;
        }
        for (char harf : tel.toCharArray()) {
            if (!Character.isDigit(harf)) {
              //  throw new Exception("Telefon numarasındaki tum karakterler rakam olmalıdır!");
              return -1;
            }
        }
        if (!tel.startsWith("5")) {
            //throw new Exception("Telefon numarası 5 ile başlamalıdır");
            return 0;
        }
        return 1;
    }
}

