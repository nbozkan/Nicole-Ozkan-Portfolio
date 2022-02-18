var moneyAdded = 0;

$(document).ready(function () {
    loadItems();
    
    $('#addDollar').on('click', function () {
        moneyAdded += 1;
        totalMoneyAdded(moneyAdded);
    });

    $('#addQuarter').on('click', function() {
        moneyAdded += 0.25;
        totalMoneyAdded(moneyAdded);
    });

    $('#addDime').on('click', function() {
        moneyAdded += 0.10;
        totalMoneyAdded(moneyAdded);
    });

    $('#addNickel').on('click', function() {
        moneyAdded += 0.05;
        totalMoneyAdded(moneyAdded);
    });

    $('#makePurchase').on('click', function() { 
        if ($('#itemNumber').val('')) {
            $('#messageBox').val('Please make a selection.');
        }

        makePurchase();
    });

    $('#changeReturn').on('click', function() {
        if($('#changeBox').val() != ''){
            clearInputs();
        } else {
            returnChange();
        }
    });
});

function loadItems() {
    var itemBlock = $('#itemGrid');

    $.ajax({
        type: 'GET',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
        success: function(itemArray) {
            $.each(itemArray, function(index, item){
                var name = item.name;
                var price = '$' + item.price;
                var quantity = 'Quantity Left: ' + item.quantity;
                var itemId = item.id;

                var itemInfo = '<div id="itemBlock" onclick="selectedItem(' + itemId + ',' + index + ')" role="button" id="item-' + itemId + '">';
                    itemInfo += '<p style="text-align: left">' + (index + 1) + '</p>';
                    itemInfo += '<p><b>' + name + '</b></p>';
                    itemInfo += '<p>' + price + '</p>';
                    itemInfo += '<p>' + quantity + '</p>';
                    itemInfo += '</div>';

                itemBlock.append(itemInfo);
            })
        },
        error: function() {
            $('#errorMessages')
                .append($('<li>')
                .attr({class: 'list-group-item list-group-item-danger'})
                .text('Error calling web service. Please try again later.'));
        }
    })
}

function selectedItem(itemId, index) {
    $('#itemNumber').val(index + 1);
    $('#itemId').val(itemId);
}

function messageBox(message) {
    $('#messageBox').val(message);
}

function totalMoneyAdded(moneyAdded) {
    $('#addMoneyAmount').empty();
    $('#addMoneyAmount').val(moneyAdded.toFixed(2));
}

function makePurchase() {
    var money = $('#addMoneyAmount').val();
    var itemIndex = $('#itemNumber').val();
    var itemId = $('#itemId').val();

    var message = "";

        var insufficientFunds = checkAndDisplayValidationErrors($('#addMoneyForm').find('input'));
        if(insufficientFunds) {
            $('#messageBox').val("Insufficient funds");
            return false;
        }

        var soldOut = checkAndDisplayValidationErrors($('#messageForm').find('input'));
        if(soldOut) {
            $('#messageBox').val('SOLD OUT!!!');
            return false;
        }

    $.ajax({
        type: 'POST',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/money/' + money + '/item/' + itemId,
        success: function (returnMoney) {
            var change = $('#changeBox');
            $('#messageBox').val("Thank You!!!");
            var pennies = returnMoney.pennies;
            var nickels = returnMoney.nickels;
            var dimes = returnMoney.dimes;
            var quarters = returnMoney.quarters;
            var returnMessage = makeChange(quarters, dimes, nickels, pennies);

            change.val(returnMessage);
            money.val('');
            loadItems();
            moneyAdded = 0;
        },
        error: function(errorReturn) {
            $('#messageBox').val(errorReturn.responseJSON.message);
    
        }
    });
}

function returnChange() {
    var money = $('#addMoneyAmount');
    var change = $('#changeBox');
    var moneyNum = money.val();

    var quarter = Math.floor(moneyNum / 0.25);
    moneyNum = (moneyNum - quarter * 0.25).toFixed(2);
    var dime = Math.floor(moneyNum / 0.10);
    moneyNum = (moneyNum - dime * 0.10).toFixed(2);
    var nickel = Math.floor(moneyNum / 0.05);
    moneyNum = (moneyNum - nickel * 0.05).toFixed(2);
    var penny = Math.floor(moneyNum / 0.01);
    moneyNum = (moneyNum - penny * 0.01).toFixed(2);

    var returnMessage = makeChange(quarter, dime, nickel, penny);
    
    change.val(returnMessage);
    money.val('');
    moneyAdded = 0;
}

function clearInputs() {
    moneyAdded = 0;
    $('#addMoneyAmount').val('');
    $('#messageBox').val('');
    $('#itemNumber').val('');
    $('#changeBox').val('');
}

function checkAndDisplayValidationErrors(input) {
    $('#errorMessages').empty();
    $('#messageBox').empty();

    var errorMessages = [];
    
    input.each(function () {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });
    if (errorMessages.length > 0){
        $.each(errorMessages, function(index, message) {
            $('#messageBox').val(message);
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}

function makeChange(quarters, dimes, nickels, pennies) {
    var returnMessage = "";

            if (quarters != 0) {
                if (quarters == 1) {
                    returnMessage += quarters + ' quarter ';
                }
                else if (quarters > 1) {
                    returnMessage += quarters + ' quarters ';
                }
            }
            if (dimes != 0) {
                if (dimes == 1) {
                    returnMessage += dimes + ' dime ';
                }
                else if (dimes > 1) {
                    returnMessage += dimes + ' dimes ';
                }
            }
            if (nickels != 0) {
                if (nickels == 1) {
                    returnMessage += nickels + ' nickel ';
                }
                else if (nickels > 1) {
                    returnMessage += nickels + ' nickels ';
                }
            }
            if (pennies != 0) {
                if (pennies == 1) {
                    returnMessage += pennies + ' penny';
                }
                else if (pennies > 1) {
                    returnMessage += pennies + ' pennies';
                }
            }
            if (quarters == 0 && dimes == 0 && nickels == 0 && pennies == 0) {
                returnMessage += 'There is no change.';
            }

    return returnMessage;
}