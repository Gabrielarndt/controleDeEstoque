angular.module('app').controller('ProductDeleteController', ['$scope', '$routeParams', '$location', 'ProductService', function($scope, $routeParams, $location, ProductService) {
    $scope.productId = $routeParams.id;

    $scope.confirmDelete = function() {
        ProductService.delete($scope.productId).then(function() {
            $location.path('/products');
        });
    };

    $scope.cancelDelete = function() {
        $location.path('/products');
    };
}]);
