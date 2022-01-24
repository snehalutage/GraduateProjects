/* Create the Table showing the details of each OTC Category */

function createTable(otcData, divId) {
  console.log("Table Data :", otcData)
  var table = d3.select(divId).append('table');
  var keys = Object.keys(otcData[0])

  //Set the opacity
  var otherOpacityHover = "0.1"

  /* Create the Table */
  var thead = table.append("thead");
  var tbody = table.append("tbody");

  thead.append("tr")
    .selectAll("th")
    .data(keys)
    .join("th")
    .text(d => d);

  var drugs = tbody.selectAll('tr.drug')
    .data(otcData)
    .join('tr')
    .attr('class', 'drug')

  drugs.selectAll('td')
    .data(d => keys.map(k => d[k]))
    .join('td')
    .text(d => d)

  /* Mouseover event */
  drugs.on('mouseover', function (event, d) {
    d3.select(event.currentTarget)
      .classed('highlight', true)

    d3.selectAll('#chart circle').filter(dd => dd.data.name === d.OTC_Drug_Type)
      .classed('highlight', true)

    d3.selectAll('#chart circle').filter(dd => dd.data.name !== d.OTC_Drug_Type)
      .style('fill-opacity', otherOpacityHover)

    d3.selectAll('#linechart path.line').filter(dd => dd.name === d.OTC_Drug_Type)
      .classed('highlight', true)
  })

  /* Mouseout event */
  drugs.on('mouseout', function (event, d) {
    d3.select(event.currentTarget)
      .classed('highlight', false)

    d3.selectAll('#chart circle').filter(dd => dd.data.name === d.OTC_Drug_Type)
      .classed('highlight', false)

    d3.selectAll('#linechart path.line').filter(dd => dd.name === d.OTC_Drug_Type)
      .classed('highlight', false)

    d3.selectAll('#chart circle').filter(dd => dd.data.name !== d.OTC_Drug_Type)
      .style('fill-opacity', 0.7);
  })
}