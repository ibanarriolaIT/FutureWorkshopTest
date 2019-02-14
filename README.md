# FW Code Test Android

## Design/Implementation decisions

### Login
For the login screen I added a new condition in the RESTMockServer and add a success message if the user and password are the proper ones (hardcoded) and also different error messages depending if a field is empty (I handle it in the LoginActivity), if a user is incorrect or if the password is incorrect. I added hints in both fields with the proper user and password so it can be easy to enter

### BasePresenter and BaseView
I created a generic `BasePresenter` class in order to handle the operations that all the presenter will have to do as attach a view when the activity/fragment is created, add disposable subscriptions every time a call to the server is made and detach view when changing activity/fragment. This `BasePresenter` will extend a `BaseView` interface that the activity/fragment will implement. The `attachView` method have to be a `BaseView` View. The `onInit` method will be use to attach the view and make the calls to the presenter that will have to be done on view creation.

### Adapter reuse
Since the data type used in `BreedListFragment` and `FavouriteFragment` is the same it has no sense to create 2 different adapters and 2 different layouts for that. So I decided to use the same layout for both fragments and the same adapter.

### BreedDetailsFragment
When you click on an image in both `BreedListFragment` and `FavouriteFragment` you go to this fragment so I had to implement different functionalities for each. I decided to do so because the appearance and functionalities are the same. The main differences are the following: 
- If I am coming from `BreedListFragment` I get the stats from the mock server and I also have to check if it is favourite or not. 
- If I am coming from `FavouriteFragment` I get the stats from room repository and I don't check if it is favourite or not because I know it is.

### Use cases
I decided to add one layer more between the repository and the presenter for the the business logic so the repository will be just in charge of contacting with the server and returning its value and the presenter just have to get the result with the proper object and tell to the activity what he has to show. 

### JSON and XML calls
For the problem of having to use Retrofit with JSON and XML response, I created another rest manager (called `XMLRestManager`) to the one that it was created in the first version of the test. So the repository that will call the services who return XML format data will have this `XMLRestManager` and the ones that calls to services who return JSON data will call to `RestManager`. All this is managed using dagger. 

### Room
With the calls to the persistence data, in the usecase I added a find call before calling to add/remove to make sure that when I want to remove a breed this breed is in the room database. Otherwise when I want to add a breed, first I check that the breed was not already there. Once I know that I can add/remove data I make a `Completable.mergeArray` with to call to the `RoomRepository` 2 times, one to add the breed and the other to add its stats. This completable will only success if both operation succeed. Finally I also added error messages for the cases described.

### Error handling
For error handling, I created an ErrorHandler class with a static method to convert a throwable in the message string that is stored in the error body of the response.


## Used libraries

Besides de libraries provided in the initial version of the test, I also added the following libraries:

- Glide: I choose this library before Picasso because it consume less memory and is faster loading cache images. 
- Room: This library also consumes less ram that Realm and the size of the library is smaller
- DataBinding: This is not a library but with this I avoid to use ButterKnife in Fragments and in the RecyclerView adapter (in the Activities I used ButterKnife just to show I can use it as well). DataBinding allows me to decrease the code lines in the fragments and the adapter since I don't have to go one by one setting the data to my layout components. 