var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider) {
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
});

app.controller('ProductController', function($scope, $http) {
    $scope.products = [];
    $scope.product = {};

    $scope.getProducts = function() {
        $http.get('http://localhost:8080/api/products').then(function(response) {
            $scope.products = response.data;
        });
    };

    $scope.createProduct = function() {
        $http.post('http://localhost:8080/api/products', $scope.product).then(function(response) {
            $scope.product = {};
            $scope.getProducts();
        });
    };

    $scope.updateProduct = function() {
        $http.put('http://localhost:8080/api/products/' + $scope.product.id, $scope.product).then(function(response) {
            $scope.getProducts();
        });
    };

    $scope.deleteProduct = function(id) {
        $http.delete('http://localhost:8080/api/products/' + id).then(function(response) {
            $scope.getProducts();
        });
    };

    $scope.getProducts();
});
