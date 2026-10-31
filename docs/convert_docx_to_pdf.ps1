$word = New-Object -ComObject Word.Application
$word.Visible = $false
try {
    $docxPath = "D:\SemiOODPII\docs\SmartCity360_Project_Report.docx"
    $pdfPath = "D:\SemiOODPII\docs\SmartCity360_Project_Report.pdf"
    $doc = $word.Documents.Open($docxPath)
    $doc.SaveAs([ref]$pdfPath, [ref]17)
    $doc.Close([ref]0)
    Write-Host "Updated PDF Successfully from DOCX at $pdfPath"
} catch {
    Write-Error $_.Exception.Message
} finally {
    $word.Quit()
    [System.Runtime.Interopservices.Marshal]::ReleaseComObject($word) | Out-Null
}
