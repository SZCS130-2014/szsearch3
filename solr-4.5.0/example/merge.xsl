<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:key name="k1" match="product" use="PID"/>

<xsl:template match="@* | node()">
  <xsl:copy>
    <xsl:apply-templates select="@* | node()"/>
  </xsl:copy>
</xsl:template>

<xsl:template match="doc">
  <xsl:variable name="PID" select="PID"/>
  <xsl:apply-templates select="@* | PID"/>
  <xsl:for-each select="document('Dataset2.xml')">
    <xsl:apply-templates select="key('k1', $PID)/Category | key('k1', $id)/DisplayName"/>
  </xsl:for-each>
  <xsl:apply-templates select="Comments"/>
</xsl:template>

</xsl:stylesheet>