package com.braguia.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class TrailInfo {

    @SerializedName("id")
    String id;

    @SerializedName("trail_img")
    String image_url;

    @SerializedName("rel_trail")
    List<JSONArray> rel_trail;

    @SerializedName("edges")
    List<JSONArray> edges;

    @SerializedName("trail_name")
    String trail_name;

    @SerializedName("trail_desc")
    String trail_desc;

    @SerializedName("trail_duration")
    Integer trail_duration;

    @SerializedName("trail_difficulty")
    String trail_difficulty;




//
//    {
//        "id": 1,
//            "trail_img": "http://c5a2-193-137-92-29.eu.ngrok.io/media/se_de_braga_m0C5XV9.jpg",
//            "rel_trail": [
//        {
//            "id": 1,
//                "value": "No",
//                "attrib": "Hiking",
//                "trail": 1
//        }
//        ],
//        "edges": [
//        {
//            "id": 1,
//                "edge_start": {
//            "id": 2,
//                    "rel_pin": [
//            {
//                "id": 1,
//                    "value": "yes",
//                    "attrib": "Wheelchair accessible",
//                    "pin": 2
//            }
//                    ],
//            "media": [
//            {
//                "id": 1,
//                    "media_file": "http://c5a2-193-137-92-29.eu.ngrok.io/media/se_de_braga_m0C5XV9.jpg",
//                    "media_type": "I",
//                    "media_pin": 2
//            },
//            {
//                "id": 2,
//                    "media_file": "http://c5a2-193-137-92-29.eu.ngrok.io/media/A_Alma_Gente_Se_de_Braga.mp3",
//                    "media_type": "R",
//                    "media_pin": 2
//            }
//                    ],
//            "pin_name": "Sé de Braga",
//                    "pin_desc": "The Sé of Braga, also known as the Braga Cathedral, is a magnificent example of Baroque architecture located in the historic city of Braga, Portugal. This grand cathedral was built in the 11th century, and has undergone numerous renovations and additions over the centuries, resulting in a stunning blend of architectural styles.\r\n\r\nThe exterior of the cathedral is impressive, with intricate stone carvings, towering bell towers, and a grandiose façade that features a large rose window and statues of saints. The cathedral's interior is just as awe-inspiring, with a nave and transept that are richly adorned with paintings, sculptures, and gold leaf accents.",
//                    "pin_lat": 41.55008,
//                    "pin_lng": -8.4268,
//                    "pin_alt": 450.0
//        },
//            "edge_end": {
//            "id": 4,
//                    "rel_pin": [],
//            "media": [
//            {
//                "id": 3,
//                    "media_file": "http://c5a2-193-137-92-29.eu.ngrok.io/media/guia_bom_jesus.webm",
//                    "media_type": "V",
//                    "media_pin": 4
//            }
//                    ],
//            "pin_name": "Bom Jesus",
//                    "pin_desc": "Bom Jesus de Braga",
//                    "pin_lat": 41.5547,
//                    "pin_lng": -8.377,
//                    "pin_alt": 550.0
//        },
//            "edge_transport": "D",
//                "edge_duration": 15,
//                "edge_desc": "Depends on traffic",
//                "edge_trail": 1
//        },
//        {
//            "id": 2,
//                "edge_start": {
//            "id": 4,
//                    "rel_pin": [],
//            "media": [
//            {
//                "id": 3,
//                    "media_file": "http://c5a2-193-137-92-29.eu.ngrok.io/media/guia_bom_jesus.webm",
//                    "media_type": "V",
//                    "media_pin": 4
//            }
//                    ],
//            "pin_name": "Bom Jesus",
//                    "pin_desc": "Bom Jesus de Braga",
//                    "pin_lat": 41.5547,
//                    "pin_lng": -8.377,
//                    "pin_alt": 550.0
//        },
//            "edge_end": {
//            "id": 3,
//                    "rel_pin": [],
//            "media": [],
//            "pin_name": "Sameiro",
//                    "pin_desc": "The Sanctuary of Sameiro is a stunning religious site located in Braga, Portugal. Perched on a hilltop overlooking the city, this sanctuary is a popular pilgrimage destination for Catholics and tourists alike.\r\n\r\nThe sanctuary's history dates back to the late 19th century, when a group of faithful Catholics decided to build a church dedicated to the Virgin Mary. Construction began in 1863, and it took over a century to complete. The result is an awe-inspiring neo-classical basilica with a soaring bell tower, intricate carvings, and stunning stained-glass windows.\r\n\r\nThe interior of the sanctuary is just as impressive, with ornate altars, murals, and sculptures. Visitors can admire the beauty of the nave and transept, as well as the magnificent organ and choir loft. The highlight of the sanctuary is the magnificent image of Our Lady of Sameiro, a statue of the Virgin Mary that is revered by Catholics around the world.\r\n\r\nOutside the sanctuary, visitors can explore the beautiful gardens, fountains, and monuments. The grounds are dotted with statues of saints and other religious figures, as well as chapels and shrines dedicated to various saints. The views from the hilltop are breathtaking, with panoramic vistas of the city and surrounding countryside.",
//                    "pin_lat": 41.54184,
//                    "pin_lng": -8.369803,
//                    "pin_alt": 600.0
//        },
//            "edge_transport": "D",
//                "edge_duration": 5,
//                "edge_desc": "Bumpy road",
//                "edge_trail": 1
//        },
//        {
//            "id": 3,
//                "edge_start": {
//            "id": 3,
//                    "rel_pin": [],
//            "media": [],
//            "pin_name": "Sameiro",
//                    "pin_desc": "The Sanctuary of Sameiro is a stunning religious site located in Braga, Portugal. Perched on a hilltop overlooking the city, this sanctuary is a popular pilgrimage destination for Catholics and tourists alike.\r\n\r\nThe sanctuary's history dates back to the late 19th century, when a group of faithful Catholics decided to build a church dedicated to the Virgin Mary. Construction began in 1863, and it took over a century to complete. The result is an awe-inspiring neo-classical basilica with a soaring bell tower, intricate carvings, and stunning stained-glass windows.\r\n\r\nThe interior of the sanctuary is just as impressive, with ornate altars, murals, and sculptures. Visitors can admire the beauty of the nave and transept, as well as the magnificent organ and choir loft. The highlight of the sanctuary is the magnificent image of Our Lady of Sameiro, a statue of the Virgin Mary that is revered by Catholics around the world.\r\n\r\nOutside the sanctuary, visitors can explore the beautiful gardens, fountains, and monuments. The grounds are dotted with statues of saints and other religious figures, as well as chapels and shrines dedicated to various saints. The views from the hilltop are breathtaking, with panoramic vistas of the city and surrounding countryside.",
//                    "pin_lat": 41.54184,
//                    "pin_lng": -8.369803,
//                    "pin_alt": 600.0
//        },
//            "edge_end": {
//            "id": 1,
//                    "rel_pin": [],
//            "media": [],
//            "pin_name": "Casa do Porto Povoa Lanhoso",
//                    "pin_desc": "A Casa do Porto is a meeting place for FC Porto fans, typically found in cities and towns across Portugal. These cozy establishments are often adorned with the team's blue and white colors and decorated with memorabilia such as flags, jerseys, and scarves.\r\n\r\nInside a typical Casa do Porto, visitors can expect to find a lively and welcoming atmosphere. Fans gather to watch live matches on big screen TVs, cheer on the team, and share their passion for the club. Many establishments also serve traditional Portuguese dishes and drinks, adding to the convivial ambiance.",
//                    "pin_lat": 41.575,
//                    "pin_lng": -8.2671,
//                    "pin_alt": 400.0
//        },
//            "edge_transport": "D",
//                "edge_duration": 30,
//                "edge_desc": "Depends on traffic",
//                "edge_trail": 1
//        }
//        ],
//        "trail_name": "Holy Trail",
//            "trail_desc": "Holy Trail",
//            "trail_duration": 180,
//            "trail_difficulty": "E"
//    }

}
