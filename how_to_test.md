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
To obtain JWT token you send GET request to user service as below:  
`curl -X POST -d "user=ali" http://localhost:8080/api/v1/user/getToken`
After run above command , result would be as below:
>`{
"user":"ali",
"token":"Bearer **JWT TOKEN**"
}`

**NOTE:**  
We allow any user of these parameters to pass through. This was done intentionally because of provide facilities just for test, Although we do it by `in-memory` auth meanwhile.  


