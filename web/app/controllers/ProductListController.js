angular.module('app').controller('ProductListController', ['$scope', 'ProductService', function($scope, ProductService) {
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
        ProductService.delete(id).then(function() {
            $scope.loadProducts();
        });
    };

    $scope.loadProducts();
}]);
