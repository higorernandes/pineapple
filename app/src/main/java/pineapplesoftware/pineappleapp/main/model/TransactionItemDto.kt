package pineapplesoftware.pineappleapp.main.model;

import java.util.*

/**
 * Created by Higor Ernandes on 28/06/17.
 */

class TransactionItemDto constructor(transactionId: Int?,  transactionName: String?, transactionDescription: String?, transactionType: ExpenseType?, transactionDate: Date?, transactionAccount: AccountDto?, transactionAmount: String?) {

    var transactionId : Int? = null
    var transactionName : String? = null
    var transactionDescription : String? = null
    var transactionType : ExpenseType? = null
    var transactionDate: Date? = null
    var transactionAccount : AccountDto? = null
    var transactionAmount : String? = null

    init {
        this.transactionId = transactionId
        this.transactionName = transactionName
        this.transactionDescription = transactionDescription
        this.transactionType = transactionType
        this.transactionDate = transactionDate
        this.transactionAccount = transactionAccount
        this.transactionAmount = transactionAmount
    }

    enum class ExpenseType {
        EXPENSE, INCOME
    }
}
