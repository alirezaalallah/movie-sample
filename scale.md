### How we can scale movie service?
This is written base microservice approach and easy to scale and increase instances and use cloud platforms like kubernetes to scale this application , also we can enable cache mechanism and sync data with API to improve our performance however OMDB API has limitation for usage per day.  
* user in-memory solution to save and calculate rates
* sync data with OMDB API
* use cloud platforms line kubernetes and scale service
* enable circuit breaker to handle user request when API couldn't respond to us,