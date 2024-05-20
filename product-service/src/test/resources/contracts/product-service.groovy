package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/api/products/1'
    }
    response {
        status OK()
        body("""
            {
                "id": 1,
                "name": "Product 1",
                "price": 19.99,
                "stock": 10
            }
        """)
        headers {
            contentType('application/json')
        }
    }
}