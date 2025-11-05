use grocery_app;

db.createCollection("customers");

db.customers.createIndex(
{phone:1},
{unique:true});

db.counters.insertOne({
  _id: "customerId",
  sequence_value: 0
});

db.customers.insertOne([{
  name: "Marudhu",
  email: "marudhu@gmail.com",
  phone: "9876543210",
  addresses: [
    {
      doorNo: 123,
      street: "Ambedkar Street",
	  area: "Redhills",
      city: "Chennai",
      state: "TN",
      pincode: "600001",
      type:"home"
    }
  ],
  createdAt: new Date()
}
);