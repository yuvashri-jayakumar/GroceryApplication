use grocery_app;

db.createCollection("customers");

db.createCollection("categories");

db.createCollection("products");


db.createCollection("orders");

db.createCollection("payments");


--- display all colelctions 
show collections

--metadata info

db.getCollectionInfos()

db.customers.createIndex(
{phone:1},
{unique:true});

db.customers.insertMany([{
  name: "Marudhu",
  email: "marudhu@gmail.com",
  phone: "9876543210",
  addresses: [
    {
      addressId: ObjectId(),
      street: "123 Ambedkar Street",
	  area: "Redhills",
      city: "Chennai",
      state: "TN",
      pincode: "600001"
    }
  ],
  createdAt: new Date()
},
{
  name: "Yuvashri",
  email: "yuva@gmail.com",
  phone: "1234567890",
  addresses: [
    {
      addressId: ObjectId(),
      street: "123 Nehru Street",
	  area: "Anna nagar",
      city: "Chennai",
      state: "TN",
      pincode: "600501"
    }
  ],
  createdAt: new Date()
},
{
  name: "Marudhu",
  email: "marudhu@gmail.com",
  phone: "1234569872",
  addresses: [
    {
      addressId: ObjectId(),
      street: "MDN Street",
	  area: "Koyambedu",
      city: "Chennai",
      state: "TN",
      pincode: "600011"
    }
  ],
  createdAt: new Date()
}]
);


db.categories.createIndex(
{name:1},{unique:true});


db.categories.insertMany([
  {
    name: "Fruits",
    description: "Fresh and seasonal fruits"
  },
  {
    name: "Vegetables",
    description: "Farm-fresh vegetables"
  },
  {
    name: "Dairy",
    description: "Milk, butter, cheese, and more"
  },
  {
    name: "Bakery",
    description: "Bread, buns, cakes and baked snacks"
  },
  {
    name: "Beverages",
    description: "Juices, soft drinks, tea and coffee"
  },
  {
    name: "Snacks",
    description: "Chips, biscuits and munchies"
  }
]);

db.categories.insertMany([
  {
    name: "Food",
    description: "Rice & Grains, Flour, Staples"
  },
  {
  
  name: "Pulses",
    description: "Dhal, Lentils"
}]);
  


---Set Auto increment value for products --- 

db.counters.insertOne({
  _id: "productId",
  sequence_value: 0
});


function getNextProductId(){

const result = db.counters.findOneAndUpdate(

{_id : "productId"},
{$inc : {sequence_value : 1}},
{returnNewDocument:true}
);
return result.sequence_value;
};

db.createCollection("products", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["productId", "name", "categoryId", "price", "stockQty"],
      properties: {
        productId: {
          bsonType: "int",
          description: "Auto-increment product ID (integer) is required"
        },
        name: {
          bsonType: "string",
          description: "Product name is required"
        },
        categoryId: {
          bsonType: "objectId",
          description: "Must reference a valid category _id"
        },
        price: {
          bsonType: "number",
          description: "Product price is required"
        },
        stockQty: {
          bsonType: "int",
          description: "Stock quantity must be integer"
        },
        brand: {
          bsonType: "string",
          description: "Brand name optional"
        },
        unitSize: {
          bsonType: "string",
          description: "e.g. 1 kg, 500 g"
        },
        description: {
          bsonType: "string"
        }
      }
    }
  },
  validationLevel: "moderate"
});
db.products.createIndex(
{name:1,brand:1,unitSize:1},{unique:true}
);
---Add Products 

db.products.insertMany([
  {
    productId: getNextProductId(),
    name: "Basmati Rice",
    categoryId: ObjectId("68fc82ca972b439a8f1b4d6c"), // Staples
    brand: "India Gate",
    unitSize: "10 kg",
    price: 1100,
    stockQty: 50,
    description: "Premium long-grain basmati rice."
  },
  {
    productId: getNextProductId(),
    name: "Wheat Flour",
    categoryId: ObjectId("68fc82ca972b439a8f1b4d6c"),
    brand: "Aashirvaad",
    unitSize: "10 kg",
    price: 520,
    stockQty: 80,
    description: "High-quality whole wheat flour."
  },
  { productId: getNextProductId(),
    name: "Apple",
    categoryId: ObjectId("68fc7e57972b439a8f1b4d66"),
    brand: "Fresh Farm",
    unitSize: "1 kg",
    price: 180,
    stockQty: 50,
    description: "Fresh and crispy apples."
  },
  {
    productId: getNextProductId(),
    name: "Banana",
    categoryId: ObjectId("68fc7e57972b439a8f1b4d66"),
    brand: "Local",
    unitSize: "1 dozen",
    price: 50,
    stockQty: 120,
    description: "Organic ripe bananas."
  },
  {
    productId: getNextProductId(),
    name: "Carrot",
    categoryId: ObjectId("68fc7e57972b439a8f1b4d67"),
    brand: "Local",
    unitSize: "1 kg",
    price: 40,
    stockQty: 200,
    description: "Fresh and healthy carrots."
  },
  {
        productId: getNextProductId(),
    name: "Milk",
    categoryId: ObjectId("68fc7e57972b439a8f1b4d68"),
    brand: "Amul",
    unitSize: "1 litre",
    price: 55,
    stockQty: 150,
    description: "Toned milk from Amul."
  },
  {
        productId: getNextProductId(),
    name: "Bread",
    categoryId: ObjectId("68fc7e57972b439a8f1b4d69"),
    brand: "Britannia",
    unitSize: "400g",
    price: 35,
    stockQty: 60,
    description: "Soft and fresh white bread."
  }
]);

--Add validator to products --


---Modify existing collections -- 
db.runCommand({
  collMod: "products",
  validator : {

$jsonSchema: {

bsonType : "object",
required : ["name","categoryId","price"],
properties : {
name : {bsonType : "string"},
categoryId : {bsonType : "objectId"},
price : {bsonType : "number"},
stockQty : {bsonType : "int"}
}
}
}
});


db.createCollection("orders", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["orderId", "customerId", "orderDate", "items", "totalAmount", "orderStatus"],
      properties: {
        orderId: {
          bsonType: "int",
          description: "Auto-increment order id"
        },
        customerId: {
          bsonType: "objectId",
          description: "Must reference customers._id"
        },
        orderDate: {
          bsonType: "date",
          description: "Order date required"
        },
        orderStatus: {
          enum: ["Pending", "Processing", "Shipped", "Delivered", "Cancelled"],
          description: "Order status required"
        },
        totalAmount: {
          bsonType: "number",
          description: "Order total required"
        },
        items: {
          bsonType: "array",
          minItems: 1,
          items: {
            bsonType: "object",
            required: ["productId", "quantity", "price"],
            properties: {
              productId: { bsonType: "int" },
              quantity: { bsonType: "int" },
              price: { bsonType: "number" }
            }
          }
        }
      }
    }
  },
  validationLevel: "moderate"
});
db.orders.createIndex({ orderId: 1 }, { unique: true });
db.counters.insertOne({
  _id: "orderId",
  sequence_value: 0
});

function getNextOrderId(){

const result = db.counters.findOneAndUpdate(

{_id : "orderId"},
{$inc : {sequence_value : 1}},
{returnNewDocument:true}
);
return result.sequence_value;
};

db.runCommand({
  collMod: "orders",
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["orderId", "customerId", "orderDate", "items", "totalAmount", "orderStatus", "paymentStatus"],
      properties: {
        orderId: {
          bsonType: "int",
          description: "Auto-increment order id"
        },
        customerId: {
          bsonType: "objectId",
          description: "Must reference customers._id"
        },
        addressId: {
          bsonType: "objectId",
          description: "Must reference customers.address._id"
        },
        orderDate: {
          bsonType: "date",
          description: "Order date required"
        },
        items: {
          bsonType: "array",
          minItems: 1,
          items: {
            bsonType: "object",
            required: ["productId", "quantity", "price"],
            properties: {
              productId: { bsonType: "int" },
              quantity: { bsonType: "int" },
              price: { bsonType: "number" }
            }
          }
        },
        totalAmount: {
          bsonType: "number",
          description: "Total amount required"
        },
        orderStatus: {
          enum: ["Pending", "Confirmed", "Shipped", "Delivered", "Cancelled"],
          description: "Must be a valid status"
        },
        paymentStatus: {
          enum: ["Pending", "Paid", "Refunded"],
          description: "Valid payment status required"
        }
      }
    }
  },
  validationLevel: "moderate"
});

db.orders.insertMany([
  {
    orderId: getNextOrderId(),
    customerId: ObjectId("68fc7da0972b439a8f1b4d5d"),
    addressId: ObjectId("68fc7da0972b439a8f1b4d5a"),
    items: [
      { productId: 3, name: "Apple", price: 180, quantity: 2 },
      { productId: 4, name: "Banana", price: 50, quantity: 1 }
    ],
    totalAmount: 410,
    orderStatus: "Confirmed",
    paymentStatus: "Paid",
    orderDate: new Date()
  },
  {
    orderId:getNextOrderId(),
    customerId: ObjectId("68fc7da0972b439a8f1b4d5d"),
    addressId:ObjectId("68fc7da0972b439a8f1b4d5a"),
    items: [
      { productId: 1, name: "Basmati Rice", price: 650, quantity: 1 }
    ],
    totalAmount: 650,
    orderStatus: "Pending",
    paymentStatus: "Pending",
    orderDate: new Date()
  },
  {
    orderId: getNextOrderId(),
    customerId: ObjectId("68fc7da0972b439a8f1b4d5e"),
    addressId:ObjectId("68fc7da0972b439a8f1b4d5b"),
    items: [
      { productId: 6, name: "Milk", price: 55, quantity: 3 },
      { productId: 7, name: "Bread", price: 35, quantity: 2 }
    ],
    totalAmount: 235,
    orderStatus: "Shipped",
    paymentStatus: "Paid",
    orderDate: new Date()
  }
]);


---Aggregation Pipeline Examples---
---$match

---Filter the products by name 

db.products.aggregate({

$match : {name :"Basmati Rice"}
});

--$group 

--- GEt the orders grouping the customeres

db.orders.aggregate({
$group : {
_id: "$customerId",
totalOrders : {$sum : 1},
totalAmount : {$sum : "$totalAmount"}

}});

--$limit

--Limit the collections 

db.products.aggregate({
$limit  :1 });


--$project

---filter Only the specific columns 
db.products.aggregate({
$project : { "_id" : 0, "name" : 1, "description":1 }
});

---$sort 

--sort the orders with highest amount

db.orders.aggregate({
$sort : {"totalAmount" : -1}
});

--$addFields

--add new fields to the document
db.orders.aggregate({
$addFields :
{"avgPrice" : {$avg : "$items.price"}}
});


--$count 
--count the orders for specific product 

db.orders.aggregate([

 { $match : {"items.productId" : 6} },
 {$count : "totalMilkOrders"}
 ]);

--$lookup
--- left outer join with other colelction

--GEt the orders with customer name & display only the customer name and order ids


db.orders.aggregate([

{ $lookup : {
  from: "customers",
      localField: "customerId",
      foreignField: "_id",
      as: "customer_details"
}
},

{$project : {

"customer_details.name" : 1,
"orderId" :1
}}
]);


--$out

---write return documents to a new colelction 



-- $and // $or ///$nor

Get the order where the order status is confirmed and paid

db.orders.find({$and : [{"orderStatus":"Confirmed"},{"paymentStatus":"Paid"}]})

---$not 

--Get the orders where the item prce is not greater than 100 

db.orders.find({"items.price": { $not: {$gt : 100 }}});


--$skip 
--skip the documents
db.orders.find().skip(3)


--$text search
db.products.createIndex({"name":"text"});
db.products.createIndex( { "$**": "text" } )
db.products.find({ $text: { $search: "Rice" } });


---Pipeline Examples---
-- display Products with Category

db.products.aggregate([
{
$lookup : {
from : "categories",
localField:"categoryId",
foreignField:"_id",
as:"category"
}
},
{$unwind:"$category"}, --- Modify the array elemets to each document
{
$project : {
"name" :1, "brand":1, "unitSize" : 1,"price":1,"stockQty":1,"categoryName":"$category.name"

}}

]);

---Total Sales Per Product

db.orders.aggregate([
{ $unwind : "$items"},
{
$group : {"_id":"$items.productId",
"totalQuantity": {$sum: "$items.quantity"},
"totalSales":{$sum : {$multiply:["$items.price","$items.quantity"]}}
}

},
{
$lookup : 
{
from:"products",localField:"$items.productId",foreignField:"productId",as: "product"
}
},
{
$project : {
"productId" : 1,
"totalQuantity" :1,
"totalSales":1,
"product.name":1

}}

]);