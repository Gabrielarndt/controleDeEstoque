app.controller('StockActionController', ['$scope', '$http', function($scope, $http) {
    console.log('StockActionController initialized');

    $scope.stockActions = [];

    $scope.getStockActions = function() {
        console.log('Fetching stock actions');
        $http.get('http://localhost:8080/api/stock-actions').then(function(response) {
            $scope.stockActions = response.data;
            console.log('Stock actions fetched:', $scope.stockActions);
        }, function(error) {
            console.error('Error fetching stock actions:', error);
        });
    };

    $scope.restoreProduct = function(id) {
        console.log('Restoring product with id:', id);
        $http.post('http://localhost:8080/api/stock-actions/restore/' + id).then(function(response) {
            console.log('Product restored:', response.data);
            $scope.getStockActions();
        }, function(error) {
            console.error('Error restoring product:', error);
        });
    };

    $scope.getStockActions();
}]);
