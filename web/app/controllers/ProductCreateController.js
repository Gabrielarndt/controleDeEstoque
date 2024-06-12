angular.module('app').controller('ProductCreateController', ['$scope', 'ProductService', '$location', function($scope, ProductService, $location) {
    $scope.product = {};

    $scope.createProduct = function() {
        ProductService.create($scope.product).then(function(response) {
            $location.path('/products');
        });
    };
}]);
