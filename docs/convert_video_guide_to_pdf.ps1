$word = New-Object -ComObject Word.Application
$word.Visible = $false
try {
    $htmlPath = "D:\SemiOODPII\docs\SmartCity360_Video_Demonstration_Guide.html"
    $pdfPath = "D:\SemiOODPII\docs\SmartCity360_Video_Demonstration_Guide.pdf"
    $doc = $word.Documents.Open($htmlPath)
    $doc.SaveAs([ref]$pdfPath, [ref]17)
    $doc.Close([ref]0)
    Write-Host "Video Guide PDF Generated Successfully at $pdfPath"
} catch {
    Write-Error $_.Exception.Message
} finally {
    $word.Quit()
    [System.Runtime.Interopservices.Marshal]::ReleaseComObject($word) | Out-Null
}
