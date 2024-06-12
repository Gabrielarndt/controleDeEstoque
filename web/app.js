angular.module('app', ['ngRoute'])
    .constant('API_URL', 'http://localhost:8080/api/products')
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/products', {
                templateUrl: 'app/templates/product-list.html',
                controller: 'ProductListController'
            })
            .when('/products/create', {
                templateUrl: 'app/templates/product-create.html',
                controller: 'ProductCreateController'
            })
            .when('/products/edit/:id', {
                templateUrl: 'app/templates/product-edit.html',
                controller: 'ProductEditController'
            })
            .otherwise({
                redirectTo: '/products'
            });
    }]);
