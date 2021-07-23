# How to test movie service?
There is three service available in movie service as below:
* Get movie's information which base on `title` parameters,this service specified that this movie won on `Best Picture` oscar  or not.  
 **GET** `/api/v1/movie/info`
* User give rate to movies based on `title` with range 0 to 10  
  **POST**`/api/v1/movie/rate`
* Obtain top-ten rated movies based on users rates  
  **GET**`/api/v1/movie/top-ten`
* User token service to obtain JWT token belongs to user for use movie services.
**POST** `api/v1/user/getToken`


### Obtain JWT token:
All services should have authorization `JWT token` in request header that users receive from user service base on user service.  
To obtain JWT token you send request to user service as below:  
`curl -X POST -d "user=ali" http://localhost:8080/api/v1/user/getToken`
After run above command , result would be as below:
>`{
"user":"ali",
"token":"Bearer <JWT TOKEN>"
}`

**NOTE:**  
We allow any user of these parameters to pass through. This was done intentionally just for test, Although we do it by `in-memory` auth meanwhile.  

## Use JWT token and use services
Format of result describes on `README.md` file, In this section we discuss how we can use services.  
We must use JWT token in authorization header as below:    
`curl -X GET -H "Authorization: Bearer <JWT TOKEN>" SERVICE`

## EXAMPLES
### Movie info service
**URL GET:** `http://localhost:8080/api/v1/movie/info`  
**PARAM**: `title`  
**HTTP Status codes**
* **200:** in case of success response and show movie's information, in this result contains movie's information.    
* **400:** in case of fail response, In this case error field contains errors   

**Sample Test**: `curl -X GET -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/v1/movie/info?title=batman`  

**Result:**  
```
{
   "status":"OK",
   "result":{
      "title":"Batman",
      "year":"1989",
      "rated":"PG-13",
      "released":"1989-06-23",
      "runtime":126,
      "genre":"Action, Adventure",
      "director":"Tim Burton",
      "writer":"Bob Kane, Sam Hamm, Warren Skaaren",
      "actors":"Michael Keaton, Jack Nicholson, Kim Basinger",
      "plot":"The Dark Knight of Gotham City begins his war on crime with his first major enemy being Jack Napier, a criminal who becomes the clownishly homicidal Joker.",
      "language":"English, French, Spanish",
      "country":"United States, United Kingdom",
      "awards":"Won 1 Oscar. 9 wins & 26 nominations total",
      "poster":"https://m.media-amazon.com/images/M/MV5BMTYwNjAyODIyMF5BMl5BanBnXkFtZTYwNDMwMDk2._V1_SX300.jpg",
      "ratings":[
         {
            "source":"Internet Movie Database",
            "value":"7.5/10"
         },
         {
            "source":"Rotten Tomatoes",
            "value":"71%"
         },
         {
            "source":"Metacritic",
            "value":"69/100"
         }
      ],
      "metaScore":"69",
      "imdbRating":"7.5",
      "imdbVotes":"345,938",
      "imdbID":"tt0096895",
      "type":"movie",
      "boxOffice":251348343,
      "won":true
   }
}
``` 



