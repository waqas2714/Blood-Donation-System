package com.example.blooddonationsystem;

import java.util.Arrays;
import java.util.List;

public class Cities {
    private static List<String> pakistanCitiesList = Arrays.asList(
            "Karachi", "Lahore", "Islamabad", "Rawalpindi", "Faisalabad", "Multan", "Gujranwala",
            "Quetta", "Peshawar", "Sialkot", "Bahawalpur", "Sargodha", "Sukkur", "Larkana",
            "Sheikhupura", "Jhang", "Rahim Yar Khan", "Gujrat", "Mardan", "Kasur", "Mingora",
            "Dera Ghazi Khan", "Nawabshah", "Sahiwal", "Mirpur Khas", "Okara", "Mandi Bahauddin",
            "Jacobabad", "Jhelum", "Khanewal", "Khairpur", "Khuzdar", "Pakpattan", "Hub",
            "Abbottabad", "Dadu", "Gojra", "Rajanpur", "Loralai", "Dera Ismail Khan",
            "Charsadda", "Tando Allahyar", "Vihari", "Attock", "Badin", "Bahawalnagar",
            "Matli", "Ghotki", "Sibi", "Chiniot", "Kamoke", "Mianwali", "Tando Adam",
            "Haripur", "Layyah", "Swabi", "Jampur", "Kohat", "Muzaffargarh", "Muzaffarabad", "Swat"
            // Feel free to add more cities as needed
    );


    public static List<String> getCities() {
        return pakistanCitiesList;
    }

}
