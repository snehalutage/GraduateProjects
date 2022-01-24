/* Create the Line Chart */

function lineChart(data, divId)
{
  console.log("Line_chart Data", data)

  //Set the height width of the SVG
  var width = 400;
  var height = 400;
  var margin = ({ top: 20, right: 40, bottom: 30, left: 40 });
  var time_duration = 250;

  //Set the opacity parameters before and after hover
  var lineOpacity = "0.7";
  var lineOpacityHover = "0.85";
  var otherLinesOpacityHover = "0.1";
  var lineStroke = "1.5px";
  var lineStrokeHover = "2.5px";

  var circleOpacity = '0.85';
  var circleOpacityOnLineHover = "0.25"
  var circleRadius = 5;
  var circleRadiusHover = 6;

  //Get the maximum value for the Avg NADAC price(for Y-axis)
  filtered = data.map(d => d.values)
  filtered_values = filtered.map(function (d) {
    return d.map(dd => dd.value)
  })
  all_values = Array.prototype.concat.apply([], filtered_values);
  max_value = Math.max.apply(Math, all_values)

  //Format the data - date to timeformat
  var parseDate = d3.timeParse("%Y");
  data.forEach(function (d) {

    d.values.forEach(function (d) {

      d.date = parseDate(d.date);
      d.value = +d.value;
    });
  });


  /* X-axis, Y-axis scales */
  var xScale = d3.scaleTime()
    .domain(d3.extent(data[0].values, d => d.date)) // get the domain in the form [2013,2020]
    .range([margin.left, width - margin.right]);

  var yScale = d3.scaleLinear()
    .domain([0, max_value]).nice()
    .range([height - margin.bottom, margin.top])

  //Colors 
  var color = d3.scaleOrdinal(d3.schemeCategory10);

  /*X-axis and Y-axis Grids for line chart */
  xGrid = (g) => g
    .attr('class', 'grid-lines')
    .selectAll('line')
    .data(xScale.ticks())
    .join('line')
    .attr('x1', d => xScale(d))
    .attr('x2', d => xScale(d))
    .attr('y1', margin.top)
    .attr('y2', height - margin.bottom)

  yGrid = (g) => g
    .attr('class', 'grid-lines')
    .selectAll('line')
    .data(yScale.ticks())
    .join('line')
    .attr('x1', margin.left)
    .attr('x2', width - margin.right)
    .attr('y1', d => yScale(d))
    .attr('y2', d => yScale(d))

  /* Add the SVG for Line Chart*/
  var svg = d3.select(divId).append("svg")
    .attr("viewBox", [0, 0, margin.right + width + margin.left, margin.bottom + height + margin.top]);

  /* Add Lines to SVG*/
  var line = d3.line()
    .x(d => xScale(d.date))
    .y(d => yScale(d.value));

  let lines = svg.append('g')
    .attr('class', 'lines');

  lines.selectAll('.line-group')
    .data(data).enter()
    .append('g')
    .attr('class', 'line-group')
    .style("stroke-width", lineStroke)
    .on("mouseover", function (event, d) //Line on mouseover event shows the name of the drug for which the respective line on top of chart
    {
      svg.append("text")
        .attr("class", "title-text")
        .style("fill", "black")
        .text(d.name)
        .attr("text-anchor", "middle")
        .attr("x", (width) / 2)
        .attr("y", 12)
        .attr("font-size", "1em");
    })
    .on("mouseout", function (d)  //On mouseout
    {
      svg.select(".title-text").remove();
    })
    .append('path')
    .attr('class', 'line')
    .attr('d', d => line(d.values)) //create the line for each drugname
    .style('stroke', (d, i) => color(i))
    .style('opacity', lineOpacity)
    .on("mouseover", function (d) { //on mouseover reduce the opacity of the other lines, circles and change the selected lines opacity
      d3.selectAll('.line')
        .style('opacity', otherLinesOpacityHover);
      d3.selectAll('.circle')
        .style('opacity', circleOpacityOnLineHover);
      d3.select(this)
        .style('opacity', lineOpacityHover)
        .style("stroke-width", lineStrokeHover)
        .style("cursor", "pointer");
    })
    .on("mouseout", function (d) { //on mouseout event, change the opacities back
      d3.selectAll(".line")
        .style('opacity', lineOpacity);
      d3.selectAll('.circle')
        .style('opacity', circleOpacity);
      d3.select(this)
        .style("stroke-width", lineStroke)
    });


  /* Add circles on the line for each drug category */
  lines.selectAll("circle-group")
    .data(data).enter()
    .append("g")
    .attr("class", d => d.name[1])
    .style("fill", (d, i) => color(i))
    .selectAll("circle")
    .data(d => d.values).enter()
    .append("g")
    .attr("class", "circle")
    .on("mouseover", function (event, d) { //on mouse over circle, give the values for the respective point
      d3.select(this)
        .style("cursor", "pointer")
        .append("text")
        .attr("class", "text")
        .text("Value :" + d.value)
        .attr("x", d => xScale(d.date) + 5)
        .attr("y", d => yScale(d.value) - 10);
    })
    .on("mouseout", function (d) { //on mouse out, transition and remove the value
      d3.select(this)
        .style("cursor", "none")
        .transition()
        .duration(time_duration)
        .selectAll(".text").remove();
    })
    .append("circle") 
    .attr("cx", d => xScale(d.date))
    .attr("cy", d => yScale(d.value))
    .attr("r", circleRadius)
    .style('opacity', circleOpacity)
    .on("mouseover", function (d) { //Update the circle radius for the selected line
      d3.select(this)
        .transition()
        .duration(time_duration)
        .attr("r", circleRadiusHover);
    })
    .on("mouseout", function (d) {
      d3.select(this)
        .transition()
        .duration(time_duration)
        .attr("r", circleRadius);
    });


  /* Add Axis into SVG */
  var xAxis = d3.axisBottom(xScale).ticks(5);
  var yAxis = d3.axisLeft(yScale).ticks(10);

  //Add the gridlines
  svg.append('g').attr('transform', `translate(0,${height - margin.bottom})`).call(xAxis)
  svg.append('g').attr('transform', `translate(${margin.left},0)`).call(yAxis)
  svg.append('g').call(xGrid)
  svg.append('g').call(yGrid)

  //Add x-axis label
  svg.append("text")
    .attr("transform", "translate(" + (width / 2) + " ," + (height - margin.top + 20) + ")")
    .style("text-anchor", "middle")
    .text("Year");

  //Add Y-axis label
  svg.append("text")
    .attr("transform", "rotate(-90)")
    .attr("y", -6)
    .attr("x", margin.left - height / 2)
    .attr("dy", "1em")
    .style("text-anchor", "middle")
    .text("Avg NADAC per unit price");

}