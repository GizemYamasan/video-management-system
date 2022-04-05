query say_hello {
  hello(who: "gizem")
}

mutation signup_user {
  signupUser(input: {firstName: "gizem", lastName: "yamasan", email: "gizem.yamasan@gmail.com", password: "123"}) {
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

mutation login_user {
  loginUser(email: "gizem.yamasan@gmail.com", password: "123") {
    token
  }
}

mutation create_content1 {
  createContent(input: {name: "Breaking Bad", price: 1.0, type: SCIENCE_FICTION}) {
    id
    name
    price
    type
  }
}

mutation create_content2 {
  createContent(input: {name: "Emily in Paris", price: 2.0, type: DOCUMENTARY}) {
    id
    name
    price
    type
  }
}

mutation purchase_subscription($token: String!) {
  purchaseSubscription(token: $token, subscriptionType: "GOLD") {
    subscriptionType
    startDate
    endDate
    remainingAmount
  }
}

mutation purchase_content($token: String!) {
  purchaseContent(token: $token, contentId: 1) {
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
