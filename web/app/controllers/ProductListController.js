angular.module('app').controller('ProductListController', ['$scope', 'ProductService', '$location', function($scope, ProductService, $location) {
    $scope.products = [];

    $scope.loadProducts = function() {
        ProductService.getAll().then(function(response) {
            $scope.products = response.data;
        });
    };

    $scope.searchProducts = function() {
        if ($scope.searchTerm) {
            ProductService.search($scope.searchTerm).then(function(response) {
                $scope.products = response.data;
            });
        } else {
            $scope.loadProducts();
        }
    };

    $scope.deleteProduct = function(id) {
        $location.path('/products/delete/' + id);
    };

    $scope.loadProducts();
}]);
