/* Create the Checkbox For the OTC Categories */
function createCheckbox(drug_categories, divId, check = "true") {
    var list = d3.select(divId).append("ul");

    drugs_type = list.selectAll(".entry")
        .data(drug_categories)
        .join("li")
        .attr("class", "entry");
    if (check) {
        checkboxes = drugs_type.append("input")
            .attr("type", "checkbox")
            .attr("checked", "true")
            .attr("id", d => d.OTC_Drug_Type);

        /*Checkbox on change event */
        checkboxes.on("change", function (event, d) {
            display = this.checked ? "inline" : "none"
            highlight = this.checked ? "true" : "false"
            opacity = this.checked ? "0.7" : "0.1"

            // Select the repective circles on th bubble chart and reduce its opacity
            d3.selectAll("#chart circle").filter(dd => dd.data.name === d.OTC_Drug_Type)
                .style("fill-opacity", opacity);

            // For the text on the circle - reduce its opacity
            d3.selectAll("#chart .drugname").filter(function (dd) {
                if (d.OTC_Drug_Type === "Anti-Diarrheal") {
                    if (dd == "AD") { return dd }
                }
                else if (dd === d.OTC_Drug_Type[0]) {
                    return dd
                }
            })
                .style("opacity", opacity);

            //Remove/Reduce opacity of the line of respective checkbox entry on line chart
            d3.selectAll("#linechart path.line").filter(ddd => ddd.name === d.OTC_Drug_Type)
                .style("opacity", opacity)

            //Remove/Reduce opacity of the circle of respective checkbox entry on the line chart
            d3.selectAll("#linechart ." + d.OTC_Drug_Type[1]).filter(ddd => ddd.name === d.OTC_Drug_Type)
                .style("opacity", opacity)
        })
    }
    drugs_type.append("text").text(function (d) {
        if (d.OTC_Drug_Type === "Anti-Diarrheal") {
            return d.OTC_Drug_Type + " (AD)"
        }
        else {
            return d.OTC_Drug_Type + " (" + d.OTC_Drug_Type[0] + ")"
        }
    });

    /* Checkbox on hover event */
    drugs_type.on('mouseover', function (event, d) {
        d3.select(event.currentTarget)
            .classed('highlight', true)

        //Get the respective table entry and highlight it
        d3.selectAll('#table tr').filter(function (dd) { if (dd !== undefined) { if (dd.OTC_Drug_Type === d.OTC_Drug_Type) { return dd } } })
            .classed('highlight', true)
    })

    //Mouse out event
    drugs_type.on('mouseout', function (event, d) {
        d3.select(event.currentTarget)
            .classed('highlight', false)

        d3.selectAll('#table tr').filter(function (dd) { if (dd !== undefined) { if (dd.OTC_Drug_Type === d.OTC_Drug_Type) { return dd } } })
            .classed('highlight', false)
    })
}

/* Create the Checkbox For the Drugs in each OTC Categories */
function createCheckbox2(drug_categories, divId, check = "true") {
    var list = d3.select(divId).append("ul");

    drugs_type = list.selectAll(".entry")
        .data(drug_categories)
        .join("li")
        .attr("class", "entry");
    if (check) {
        checkboxes = drugs_type.append("input")
            .attr("type", "checkbox")
            .attr("checked", "true")
            .attr("display", "none")
            .attr("id", d => d);
    }
    drugs_type.append("text").text(d => d).attr("display", "none");

    /* Checkbox on change event */
    checkboxes.on("change", function (event, d) {
        display = this.checked ? "inline" : "none"
        highlight = this.checked ? "true" : "false"
        opacity = this.checked ? "0.7" : "0.1"

        //Reduce the opacity of the circle on the second bubble chart
        d3.selectAll("#linechart2 circle").filter(dd => dd.data.name === d)
            .style("fill-opacity", opacity);
    })

    /* Checkbox mouseover event */
    drugs_type.on('mouseover', function (event, d) {
        d3.select(event.currentTarget)
            .classed('highlight', true)

        //Get the respective circle based on the name 
        d3.selectAll('#linechart2 circle').filter(dd => dd.data.name == d)
            .classed('highlight', true)
    })

    /* Checkbox mouseout event */
    drugs_type.on('mouseout', function (event, d) {
        d3.select(event.currentTarget)
            .classed('highlight', false)

        //Get the respective circle based on the name 
        d3.selectAll('#linechart2 circle').filter(dd => dd.data.name == d)
            .classed('highlight', false)
    })
}

/* Create the Radio Buttons For the OTC Categories */
function radioButton(data, divId, bubble_data, drug_categories) {
    var form = d3.select(divId).append("form");

    form.selectAll("label")
        .data(data)
        .join("label")
        .text(function (d) { return d.OTC_Drug_Type; })
        .append("input")
        .attr("type", "radio")
        .attr("class", "shape")
        .attr("name", "mode")
        .attr("value", function (d, i) { return d.OTC_Drug_Type; })

    form.selectAll("label").append("br")

    /* On change event for radio button */
    button = form.selectAll("input")
    button.on("change", function (event, d) {
        selected = this.value;

        /* Update the checkbox list */
        d3.selectAll("#checkbox1 .entry").filter(dd => d.Drugs.includes(dd))
            .style("display", "inline")

        d3.selectAll("#checkbox1 .entry").filter(dd => !d.Drugs.includes(dd))
            .style("display", "none")

        /* Add the bubble chart for the respective OTC category selected in the radio buttons*/
        drugs = drug_categories.filter(dd => dd.OTC_Drug_Type === selected)
        drugs_list = drugs.map(dd => dd.Drugs)
        required_data = bubble_data.filter(ddd => drugs_list[0].includes(ddd.name))
        svg = d3.select("#linechart2")
        svg.selectAll("*").remove();
        circle_packed_chart(required_data, "#linechart2", 930, 930)
    })

}

/* Main function */
function makeChart(data) {
    //Get the respective data 
    nadac_data = data[0]; //Our filtered NADAC data
    otc_list = data[1]; //OTC drugs data

    console.log("Filtered Data :", nadac_data)
    console.log("OTC List :", otc_list)

    //Filter the data for OTC drugs only
    OTC_data = nadac_data.filter(d => d.OTC != "N")
    console.log(OTC_data)

    //Get the list of the Drug categories
    drug_categories = otc_list.OTCs;

    //To each of the entry in the nadac_data add the respective OTC category and drugname category
    copy_nadac = OTC_data
    copy_nadac.forEach(function (d) {
        drug_categories.forEach(function (drugname) {
            drugname.Drugs.forEach(function (d1) {
                if (d["NDC Description"].includes(d1)) {
                    d["OTC category"] = drugname.OTC_Drug_Type;
                    d["drugname"] = d1;
                }
            })
        })
    })

    //Format the years
    copy_nadac.forEach(function (d) {
        months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        dateFormat = months[d["Effective_Date"].split("/")[0] - 1] + "-" + d["Effective_Date"].split("/")[2].split(" ")[0]
        d["Effective_Date"] = dateFormat;
    })
    copy_nadac.forEach(function (d) {
        dateFormat = d["Effective_Date"].split("-")[1]
        d["Effective_Date"] = dateFormat;
    })

    //Aggregate the data based on year, OTC_Category and drugname category
    copy_nadac = copy_nadac.filter(d => d.drugname !== undefined)
    val2 = d3.rollup(copy_nadac, v => (d3.mean(v, d => d["NADAC_Per_Unit"])).toFixed(4), d => d["OTC category"], d => d["Effective_Date"])
    val3 = d3.rollup(copy_nadac, v => (d3.mean(v, d => d["NADAC_Per_Unit"])).toFixed(4), d => d["Effective_Date"], d => d["drugname"])

    //Generate the data in hierarchy form for bubble chart
    dataFlat = Array.from(val2.entries()).map(d => Array.from(d[1].entries()).map(dd => [d[0], dd[0], dd[1]])).flat();
    test_dataset = dataFlat.map(function (d) {
        var t = { name: d[0], title: `Drug Category : ${d[0]}, Year : ${d[1]}, Avg_NADAC_per_unit: ${d[2]}`, group: d[1], value: d[2] }
        return t;
    })

    //Create the Bubble chart
    circle_packed_chart(test_dataset, "#chart", 930, 930)

    //Create the OTC Drug types table
    createTable(drug_categories, "#table")

    //Create Checkbox
    createCheckbox(drug_categories, "#checkbox2")

    //Generate data for line chart
    test_dataset.sort(function (a, b) { return parseInt(a.group) - parseInt(b.group) })
    var grouped_line_data = []
    drug_categories.forEach(function (d) {
        value = []
        var grouped_data = test_dataset.filter(dd => dd.name === d.OTC_Drug_Type)
        var formatted_data = grouped_data.map(function (d) {
            value.push({ "date": d.group, "value": d.value })
        })
        grouped_line_data.push({ name: d.OTC_Drug_Type, values: value })
    })

    //Create the Line Chart
    lineChart(grouped_line_data, "#linechart")

    //Generate the data in hierarchy form for bubble chart for the specific drugname category (Second bubble chart)
    dataFlat_Drugs = Array.from(val3.entries()).map(d => Array.from(d[1].entries()).map(dd => [d[0], dd[0], dd[1]])).flat();
    Drugs_dataset = dataFlat_Drugs.map(function (d) {
        var t = { name: d[1], title: `Drug Name : ${d[1]}, Year : ${d[0]}, Avg_NADAC_per_unit: ${d[2]}`, group: d[0], value: d[2] }
        return t;
    })

    //Create Checkbox for each drugname category
    Drugs_list = drug_categories.map(d => d.Drugs)
    Drugs_list = Array.prototype.concat.apply([], Drugs_list);
    createCheckbox2(Drugs_list, "#checkbox1")

    //Create Radio Buttons for each OTC category
    radioButton(drug_categories, "#radio", Drugs_dataset, drug_categories)
}

//Get the data from the GITHUB
Promise.all([d3.csv("https://raw.githubusercontent.com/snehalutage/dvrepo/main/Data/data.csv", d3.autoType),
d3.json("https://raw.githubusercontent.com/snehalutage/dvrepo/main/Data/OTC_list.json")]
).then(makeChart);