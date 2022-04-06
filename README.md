# Video Management System

## Running the Application

it is a spring boot application with h2 local database which is backed by a file. 

```bash
cd video-management-system
mvn clean install
java -jar target/video-management-system-0.1.jar
```

## How to Test with GraphiQL

Open browser [localhost:8080](http://localhost:8080/graphiql) copy the queries and mutations in [this file](GRAPHIQL.md).

The test scenario is as follows:

1. Creating the user with `signup_user` mutation
1. Call `login_user` mutation with the credentials that used in `signup_user` to get the `token` please save this info.
1. Create content with `create_content1` and `create_content2` mutations
1. Use the `token` in `login_user` response to purchase subscription to user with `purchase_subscription` mutation
1. Call `purchase_content` to create the `Bill` that is asked in requirements 


## Sample Requests & Responses

#### signup_user Request
```grapgql
mutation signup_user {
  signupUser(input:{
    firstName: "gizem",
    lastName: "yamasan",
    email: "gizem.yamasan@gmail.com",
    password: "123"
  }) {
    email
    firstName
    lastName
    currentSubscription {
      remainingAmount
      subscriptionType
      startDate
      endDate
    }
  }
}
```

**Response**

```grapgql
{
  "data": {
    "signupUser": {
      "email": "gizem.yamasan@gmail.com",
      "firstName": "gizem",
      "lastName": "yamasan",
      "currentSubscription": {
        "remainingAmount": 0,
        "subscriptionType": "NO_SUBSCRIPTION",
        "startDate": "2022-04-06T00:06:40.734829",
        "endDate": "+999999999-12-31T23:59:59.999999999"
      }
    }
  }
}
```

#### login_user Request

```grapgql
mutation login_user {
  loginUser(email:"gizem.yamasan@gmail.com", password:"123") {
    token
  }
}
```

**Response**

```grapgql
{
  "data": {
    "loginUser": {
      "token": "87d71ef8-f641-4b26-8171-eeefd7adb558"
    }
  }
}
```

#### create_content1 Request 

```grapgql
mutation create_content1 {
  createContent(input:{
    name: "Breaking Bad",
    price: 1.0,
    type: SCIENCE_FICTION
  }) {
    id
    name
    price
    type
  }
}
```

**Response**

```grapgql
{
  "data": {
    "createContent": {
      "id": 1,
      "name": "Breaking Bad",
      "price": 1,
      "type": "SCIENCE_FICTION"
    }
  }
}
```

#### create_content2 Request

```grapgql
mutation create_content2 {
  createContent(input:{
    name: "Emily in Paris",
    price: 2.0,
    type: DOCUMENTARY
  }) {
    id
    name
    price
    type
  }
}
```

**Response**

```grapgql
{
  "data": {
    "createContent": {
      "id": 2,
      "name": "Emily in Paris",
      "price": 2,
      "type": "DOCUMENTARY"
    }
  }
}
```

### Subscribe 

#### purchase_subscription Request

```grapgql
mutation purchase_subscription{
  purchaseSubscription(token:"87d71ef8-f641-4b26-8171-eeefd7adb558", subscriptionType:"GOLD") {
    subscriptionType
    startDate
    endDate
    remainingAmount
  }
}
```

**Response**

```grapgql
{
  "data": {
    "purchaseSubscription": {
      "subscriptionType": "GOLD",
      "startDate": "2022-04-06T00:30:41",
      "endDate": "2022-05-06T00:30:41",
      "remainingAmount": 50
    }
  }
}
```

#### purchase_content Request

```grapgql
mutation purchase_content {
  purchaseContent(token:"f4bcdfac-8a99-4cb0-998a-48d98fff90b6", contentId: 1) {
    amount
    billNo
    content {
      id
      name
      price
      type
    }
    user {
      firstName
      lastName
      email
      currentSubscription {
        subscriptionType
        remainingAmount
        startDate
        endDate
      }
    }
  }
}
```

**Response**

```grapgql
{
  "data": {
    "purchaseContent": {
      "amount": 1,
      "billNo": "TR0001",
      "content": {
        "id": 1,
        "name": "Breaking Bad",
        "price": 1,
        "type": "SCIENCE_FICTION"
      },
      "user": {
        "firstName": "gizem",
        "lastName": "yamasan",
        "email": "gizem.yamasan@gmail.com",
        "currentSubscription": {
          "subscriptionType": "GOLD",
          "remainingAmount": 49,
          "startDate": "2022-04-06T01:19:48.811032",
          "endDate": "2022-05-06T01:19:48.811032"
        }
      }
    }
  }
}
```


