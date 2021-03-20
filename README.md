# Stock Logger

## Contributor:
#### Name: Prof. Cai Filiault

## Author:
#### Name: Ibrahim (Wusiman Yibulayin)
#### Student ID: 0728356

## Table of contents
* [Description](#description)
* [Database](#database)
* [Acknowledgments](#acknowledgments)

## Description
When we are trading stocks, we need to calculate profit or loss.
Most stock trading software does not calculate the detailed costs.
In this app, you just need to log into your bought or sold stocks.
You can estimate the selling price by setting your willing profit.

## Database
### Database Name: Stock
### Tables:
### 1. Active Stock Table
#### Table Name: Active
#### Table Columns:
<table>
    <tr>
        <th>Column Name</th>
        <th>Data Type</th>
    </tr>
    <tr>
        <td>ID</td>
        <td>int, Primary Key</td>
    </tr>
    <tr>
        <td>symbol</td>
        <td>Text</td>
    </tr>
    <tr>
        <td>companyName</td>
        <td>Text</td>
    </tr>
    <tr>
        <td>quantity</td>
        <td>int</td>
    </tr>
    <tr>
        <td>price</td>
        <td>double</td>
    </tr>
</table>

### 2. Sold Stock Table
#### Table Name: Sold
#### Table Columns:
<table>
    <tr>
        <th>Column Name</th>
        <th>Data Type</th>
    </tr>
    <tr>
        <td>ID</td>
        <td>int, Primary Key</td>
    </tr>
    <tr>
        <td>symbol</td>
        <td>Text</td>
    </tr>
    <tr>
        <td>companyName</td>
        <td>Text</td>
    </tr>
    <tr>
        <td>soldPrice</td>
        <td>double</td>
    </tr>
    <tr>
        <td>earning</td>
        <td>double</td>
    </tr>
</table>

<hr>
## Acknowledgments
I want to especially thank my teacher Prof. Cai Filiault.
He taught me how to use Android Studio to complete application
development.
