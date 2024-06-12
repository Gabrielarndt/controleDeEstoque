angular.module('app').controller('ProductEditController', ['$scope', 'ProductService', '$routeParams', '$location', function($scope, ProductService, $routeParams, $location) {
    $scope.product = {};

    $scope.loadProduct = function() {
        ProductService.getById($routeParams.id).then(function(response) {
            $scope.product = response.data;
        });
    };

    $scope.updateProduct = function() {
        ProductService.update($routeParams.id, $scope.product).then(function() {
            $location.path('/products');
        });
    };

    $scope.loadProduct();
}]);
