angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('http://localhost:8090/currencies').
                then(function(response) {
                    $scope.currencies = response.data;
                });

    $scope.getBalance = function(selectedCurrency) {
       $http.get('http://localhost:8090/balance?targetCurrencyCode=' + selectedCurrency)
             .then(function(response) {
                   $scope.balance = response.data;
               });
    }
})
.controller('ChangeCurrency', function($scope, $http) {


});