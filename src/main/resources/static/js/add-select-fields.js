function destinationHandler(e, categories, selectedCategory, selectedSubcategory) {
    clearHTML();

    let destinationName = document.querySelector("#selectDestination").value;

    if (destinationName === "all")
        return;

    const categoryName = "category";
    let sibling = document.querySelector("#direction-container");
    let categorySelect = addHTML(categoryName, sibling);

    setValues(categories, categorySelect, categoryName, selectedCategory);

    if (destinationName === "categoryName")
        return;

    const subcategoryName = "subcategory";
    categorySelect.addEventListener("change", (e) => changeSubcategorySelect(e, categories, subcategoryName));
    sibling = document.querySelector("#category-container");
    let subcategorySelect = addHTML(subcategoryName, sibling);

    let subcategories = getSubcategoryValuesByCategoryName(categorySelect.value, categories);
    setValues(subcategories, subcategorySelect, subcategoryName, selectedSubcategory);

}

function clearHTML() {
    // удалить дополнительные селекты
    const categorySelectContainer = document.querySelector("#category-container");
    categorySelectContainer?.parentNode.removeChild(categorySelectContainer);
    const subcategorySelectContainer = document.querySelector("#subcategory-container");
    subcategorySelectContainer?.parentNode.removeChild(subcategorySelectContainer);
}

function addHTML(selectName, previousSibling) {
    const template = document.getElementById("template-select");


    const categoryTemplate = template.content.cloneNode(true),
        id = "select" + selectName.charAt(0).toUpperCase() + selectName.substring(1),
        container = categoryTemplate.querySelector("div.d-flex"),
        label = categoryTemplate.querySelector("label"),
        select = categoryTemplate.querySelector("select");


    container.id = selectName + "-container";
    label.setAttribute('for', id);
    let labelText;
    switch (selectName) {
        case "category":
            labelText = "Категория:";
            break;
        case "subcategory":
            labelText = "Подкатегория:";
            break;
    }

    label.textContent = labelText;
    select.id = id;
    select.name = selectName + "Name";

    previousSibling.parentNode.insertBefore(categoryTemplate, previousSibling.nextSibling);

    return select;
}

function setValues(values, parent, name, selectValue) {
    values.forEach(val => {
        let option = document.createElement("option");
        option.value = val[name + "Name"];
        option.textContent = val[name + "Name"];
        parent.append(option);
    });

    if (selectValue)
        parent.value = selectValue;
}

function getSubcategoryValuesByCategoryName(categoryName, categories) {
    for (let category of categories) {
        if (categoryName === category.categoryName)
            return category.subcategories;
    }
    return null;
}

function changeSubcategorySelect(e, categories, name) {
    const subcategorySelect = document.querySelector("#selectSubcategory");
    clearDropDown(subcategorySelect);
    let subcategories = getSubcategoryValuesByCategoryName(e.target.value, categories);
    setValues(subcategories, subcategorySelect, name);
}

function clearDropDown(parent) {
    for (let i = parent.options.length - 1; i >= 0; i--) {
        parent.options[i] = null;
    }
}