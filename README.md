# Video Management System

## How to Test with GraphiQL

For further reference, please consider the following sections:

### Creating User



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

'''
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
'''


### Login User



mutation login_user {
  loginUser(email:"gizem.yamasan@gmail.com", password:"123") {
    token
  }
}


{
  "data": {
    "loginUser": {
      "token": "87d71ef8-f641-4b26-8171-eeefd7adb558"
    }
  }
}



### Creating Content


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


### Subscribe 

mutation purchase_subscription{
  purchaseSubscription(token:"87d71ef8-f641-4b26-8171-eeefd7adb558", subscriptionType:"GOLD") {
    subscriptionType
    startDate
    endDate
    remainingAmount
  }
}

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

### Purchase Content

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


