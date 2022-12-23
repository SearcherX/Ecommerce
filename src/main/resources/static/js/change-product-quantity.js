const formatter = new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: 'RUB',
});


async function changeQuantity(e, bucketItemsList) {
    const elemIndex = +e.target.dataset.index;
    const counter = document.querySelector(`input[type=number]#count-${elemIndex}`);
    const quantity = counter.value;
    const url = `/bucket/update?itemId=${bucketItemsList[elemIndex].id}&quantity=${quantity}`;

    const response = await fetch(url, {
        method: "POST",
    });
    const responseJson = await response.json();

    document.querySelector(".totalItems").textContent =  `${responseJson.totalItems} товара`;
    document.querySelector(".totalPrice").textContent =  `${formatter.format(responseJson.totalPrices)}`;
    document.querySelector("#price-cel-" + elemIndex).textContent = formatter.format(responseJson.bucketItems[elemIndex].totalPrice);
}
